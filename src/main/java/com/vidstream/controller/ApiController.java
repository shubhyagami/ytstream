package com.vidstream.controller;

import com.vidstream.service.SearchService;
import com.vidstream.service.StreamService;
import com.vidstream.service.TempFileManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    private static final Logger log = LoggerFactory.getLogger(ApiController.class);

    private final SearchService searchService;
    private final StreamService streamService;
    private final TempFileManager tempFileManager;

    public ApiController(SearchService searchService,
                         StreamService streamService,
                         TempFileManager tempFileManager) {
        this.searchService = searchService;
        this.streamService = streamService;
        this.tempFileManager = tempFileManager;
    }

    // ── Search ───────────────────────────────────────────────────────────────

    @GetMapping("/search")
    public ResponseEntity<?> search(
            @RequestParam String q,
            @RequestParam(defaultValue = "youtube") String source,
            @RequestParam(defaultValue = "20") int limit) {
        try {
            List<SearchService.MediaResult> results = searchService.search(q, source, limit);
            return ResponseEntity.ok(Map.of("results", results, "source", source, "query", q));
        } catch (Exception e) {
            log.error("Search error [{}] q={}: {}", source, q, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // ── Available Formats ────────────────────────────────────────────────────

    @GetMapping("/formats")
    public ResponseEntity<?> getFormats(@RequestParam String id) {
        try {
            List<StreamService.YtStreamFormat> formats = streamService.getFormats(id);
            return ResponseEntity.ok(Map.of("formats", formats));
        } catch (Exception e) {
            log.error("Formats error id={}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // ── Stream Token ─────────────────────────────────────────────────────────

    @PostMapping("/stream")
    public ResponseEntity<?> requestStream(@RequestBody Map<String, String> body) {
        String id    = body.get("id");
        String source = body.getOrDefault("source", "youtube");
        String title  = body.getOrDefault("title", "Unknown");
        String quality = body.get("quality");

        if (id == null || id.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "id is required"));
        }
        try {
            String token = streamService.prepareStream(id, source, title, quality);
            // Return mime so frontend knows audio vs video
            StreamService.StreamInfo info = streamService.getStream(token);
            String mime = info != null ? info.mimeType() : "audio/mpeg";
            String mediaType = mime.startsWith("video/") ? "video" : "audio";
            return ResponseEntity.ok(Map.of(
                "token", token, "source", source,
                "mime", mime, "mediaType", mediaType
            ));
        } catch (Exception e) {
            log.error("Stream prepare error [{}] id={}: {}", source, id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // ── Stream Delivery (supports Range) ─────────────────────────────────────

    @GetMapping("/stream/{token}")
    public ResponseEntity<StreamingResponseBody> streamMedia(
            @PathVariable String token,
            @RequestHeader(value = "Range", required = false) String rangeHeader) {
        try {
            StreamService.StreamInfo info = streamService.getStream(token);
            if (info == null) {
                return ResponseEntity.notFound().build();
            }

            long fileSize = info.size();
            long start = 0, end = fileSize - 1;
            HttpStatus status = HttpStatus.OK;

            if (rangeHeader != null && rangeHeader.startsWith("bytes=")) {
                String[] parts = rangeHeader.substring(6).split("-");
                start = Long.parseLong(parts[0]);
                if (parts.length > 1 && !parts[1].isBlank()) end = Long.parseLong(parts[1]);
                status = HttpStatus.PARTIAL_CONTENT;
            }

            final long finalStart = start;
            final long finalEnd   = end;
            final long length     = end - start + 1;

            StreamingResponseBody body = out -> {
                try (var in = java.nio.file.Files.newInputStream(info.path())) {
                    in.skipNBytes(finalStart);
                    byte[] buf = new byte[8192];
                    long remaining = length;
                    int read;
                    while (remaining > 0 &&
                           (read = in.read(buf, 0, (int) Math.min(buf.length, remaining))) != -1) {
                        out.write(buf, 0, read);
                        remaining -= read;
                    }
                } catch (IOException ignored) {}
            };

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(info.mimeType()));
            headers.setContentLength(length);
            headers.set("Accept-Ranges", "bytes");
            headers.set("Content-Range", "bytes " + start + "-" + end + "/" + fileSize);
            headers.set("Cache-Control", "no-cache");

            return ResponseEntity.status(status).headers(headers).body(body);

        } catch (Exception e) {
            log.error("Stream delivery error token={}: {}", token, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ── Stop / Cleanup ───────────────────────────────────────────────────────

    @DeleteMapping("/stream/{token}")
    public ResponseEntity<?> stopStream(@PathVariable String token) {
        tempFileManager.evict(token);
        return ResponseEntity.ok(Map.of("status", "deleted", "token", token));
    }

    // ── Health ───────────────────────────────────────────────────────────────

    @GetMapping("/health")
    public ResponseEntity<?> health() {
        return ResponseEntity.ok(Map.of("status", "ok", "service", "vidstream"));
    }
}
