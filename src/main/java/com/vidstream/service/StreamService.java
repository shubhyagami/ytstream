package com.vidstream.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.CookieManager;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
public class StreamService {

    private static final Logger log = LoggerFactory.getLogger(StreamService.class);
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/125.0.0.0 Safari/537.36";

    private static final HttpClient http = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(15))
            .followRedirects(HttpClient.Redirect.ALWAYS)
            .cookieHandler(new CookieManager())
            .build();

    @Value("${vidstream.temp-dir:/tmp/vidstream}")
    private String tempDir;

    @Value("${vidstream.ytdlp.path:yt-dlp}")
    private String ytdlpPath;

    @Value("${vidstream.rapidapi.key}")
    private String rapidApiKey;

    @Value("${vidstream.rapidapi.download-host:ytstream-download-youtube-videos.p.rapidapi.com}")
    private String rapidApiDownloadHost;

    private final TempFileManager tempFileManager;

    public StreamService(TempFileManager tempFileManager) {
        this.tempFileManager = tempFileManager;
    }

    public record StreamInfo(Path path, long size, String mimeType) {}

    /**
     * Multi-strategy download pipeline:
     * 1. RapidAPI mp36 (fast, no tools required, works on datacenter IPs)
     * 2. yt-dlp fallback (if installed)
     */
    public String prepareStream(String id, String source, String title) throws IOException, InterruptedException {
        String token = UUID.randomUUID().toString().replace("-", "");
        Path dir = Paths.get(tempDir);
        Files.createDirectories(dir);

        String videoId = extractVideoId(id, source);
        List<String> errors = new ArrayList<>();

        // ── Strategy 1: RapidAPI mp36 (YouTube → MP3) ────────────────────────
        if ("youtube".equals(source) || "spotify".equals(source)) {
            try {
                Path result = downloadWithRapidApi(videoId, dir, token);
                if (result != null) {
                    String mime = detectMime(result);
                    tempFileManager.register(token, result, mime);
                    log.info("✓ Stream ready via RapidAPI mp36: token={} size={}KB", token, Files.size(result) / 1024);
                    return token;
                }
            } catch (Exception e) {
                errors.add("RapidAPI: " + e.getMessage());
                log.warn("RapidAPI mp36 failed for {}: {}", videoId, e.getMessage());
            }
        }

        // ── Strategy 2: yt-dlp (supports all sources) ────────────────────────
        try {
            Path result = downloadWithYtDlp(id, source, dir, token);
            if (result != null) {
                String mime = detectMime(result);
                tempFileManager.register(token, result, mime);
                log.info("✓ Stream ready via yt-dlp: token={} size={}KB", token, Files.size(result) / 1024);
                return token;
            }
        } catch (Exception e) {
            errors.add("yt-dlp: " + e.getMessage());
            log.warn("yt-dlp failed for {}: {}", id, e.getMessage());
        }

        throw new IOException("All download methods failed. " + String.join(" | ", errors));
    }

    public StreamInfo getStream(String token) {
        return tempFileManager.get(token);
    }

    // ══════════════════════════════════════════════════════════════════════════
    // Strategy 1: RapidAPI ytstream-download
    // ══════════════════════════════════════════════════════════════════════════

    @SuppressWarnings("unchecked")
    private Path downloadWithRapidApi(String videoId, Path dir, String token) throws IOException, InterruptedException {
        String apiUrl = "https://" + rapidApiDownloadHost + "/dl?id=" + videoId;
        log.info("RapidAPI ytstream request: {}", apiUrl);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("x-rapidapi-host", rapidApiDownloadHost)
                .header("x-rapidapi-key", rapidApiKey)
                .header("Accept", "application/json")
                .timeout(Duration.ofSeconds(30))
                .GET()
                .build();

        HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            log.warn("RapidAPI returned status {}", response.statusCode());
            return null;
        }

        Map<String, Object> json = mapper.readValue(response.body(), new TypeReference<>() {});
        if (!"OK".equals(json.get("status"))) {
            log.warn("RapidAPI status not OK");
            return null;
        }

        // Try combined format first (itag=18 = 360p with audio)
        Object formatsObj = json.get("formats");
        if (formatsObj instanceof List) {
            for (Object f : (List<?>) formatsObj) {
                if (f instanceof Map<?, ?> fm) {
                    Object urlObj = fm.get("url");
                    String url = urlObj instanceof String s ? s : null;
                    if (url != null && !url.isBlank()) {
                        String mime = fm.get("mimeType") instanceof String s ? s : "";
                        String ext = mime.contains("audio") ? "mp3" : "mp4";
                        Path out = dir.resolve(token + "." + ext);
                        log.info("Downloading combined format: {}", fm.get("qualityLabel"));
                        downloadToFile(url, out);
                        if (Files.size(out) >= 10_000) return out;
                        Files.deleteIfExists(out);
                    }
                }
            }
        }

        // Fallback to best audio format
        Object adaptiveObj = json.get("adaptiveFormats");
        if (adaptiveObj instanceof List) {
            String bestUrl = null;
            long bestSize = 0;
            for (Object f : (List<?>) adaptiveObj) {
                if (f instanceof Map<?, ?> fm) {
                    Object mimeObj = fm.get("mimeType");
                    String mime = mimeObj instanceof String s ? s : "";
                    if (!mime.startsWith("audio/")) continue;
                    Object urlObj = fm.get("url");
                    String url = urlObj instanceof String s ? s : null;
                    if (url == null || url.isBlank()) continue;
                    long size = 0;
                    Object clObj = fm.get("contentLength");
                    if (clObj instanceof Number n) size = n.longValue();
                    if (size > bestSize) { bestSize = size; bestUrl = url; }
                }
            }
            if (bestUrl != null) {
                Path out = dir.resolve(token + ".m4a");
                downloadToFile(bestUrl, out);
                if (Files.size(out) >= 10_000) return out;
                Files.deleteIfExists(out);
            }
        }

        log.warn("RapidAPI returned no usable download URL");
        return null;
    }

    private void downloadToFile(String url, Path output) throws IOException, InterruptedException {
        HttpRequest dlReq = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", USER_AGENT)
                .timeout(Duration.ofMinutes(5))
                .GET().build();
        HttpResponse<InputStream> dlResp = http.send(dlReq, HttpResponse.BodyHandlers.ofInputStream());
        if (dlResp.statusCode() != 200) {
            throw new IOException("Download returned status " + dlResp.statusCode());
        }
        try (InputStream in = dlResp.body()) {
            Files.copy(in, output, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    // Strategy 2: yt-dlp
    // ══════════════════════════════════════════════════════════════════════════

    private Path downloadWithYtDlp(String id, String source, Path dir, String token) throws IOException, InterruptedException {
        String url = resolveUrl(id, source);
        boolean isAudio = "spotify".equals(source) || "soundcloud".equals(source);
        String ext = isAudio ? "m4a" : "mp4";
        Path outFile = dir.resolve(token + "." + ext);

        List<String> command = new ArrayList<>();
        command.add(ytdlpPath);

        if (isAudio) {
            command.add("-x");
            command.add("--audio-format"); command.add("m4a");
            command.add(url);
        } else {
            command.add("-f"); command.add("best[height<=720]");
            command.add(url);
        }

        command.add("--no-playlist");
        command.add("-o"); command.add(outFile.toAbsolutePath().toString());
        command.add("--no-warnings");

        log.info("Running: {}", String.join(" ", command));

        ProcessBuilder pb = new ProcessBuilder(command);
        pb.redirectErrorStream(true);
        Process proc = pb.start();

        String procOutput = new String(proc.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        int exitCode = proc.waitFor();

        if (exitCode != 0) {
            log.error("yt-dlp exit {}: {}", exitCode, procOutput.length() > 300 ? procOutput.substring(0, 300) : procOutput);
            throw new IOException("yt-dlp exit code " + exitCode);
        }

        // yt-dlp may use a different extension — find the actual file
        Path actual = findActualFile(dir, token);
        if (actual == null) {
            throw new IOException("Output file not found after yt-dlp");
        }

        long fileSize = Files.size(actual);
        if (fileSize < 10_000) {
            Files.deleteIfExists(actual);
            throw new IOException("Downloaded file too small (" + fileSize + " bytes)");
        }

        log.info("yt-dlp saved: {} ({} bytes)", actual.getFileName(), fileSize);
        return actual;
    }

    // ══════════════════════════════════════════════════════════════════════════
    // Helpers
    // ══════════════════════════════════════════════════════════════════════════

    private String extractVideoId(String id, String source) {
        // For YouTube, extract video ID from URL or use raw ID
        if ("youtube".equals(source)) {
            if (id.contains("watch?v=")) {
                int idx = id.indexOf("v=");
                String vid = id.substring(idx + 2);
                int amp = vid.indexOf('&');
                return amp > 0 ? vid.substring(0, amp) : vid;
            }
            return id;
        }
        // For Spotify results that fell back to YouTube search, the ID is the videoId
        if ("spotify".equals(source) && !id.startsWith("spotify:") && !id.startsWith("ytsearch")) {
            return id;
        }
        return id;
    }

    private String resolveUrl(String id, String source) {
        return switch (source.toLowerCase()) {
            case "youtube"    -> id.startsWith("http") ? id : "https://www.youtube.com/watch?v=" + id;
            case "spotify"    -> id.startsWith("spotify:") || id.startsWith("ytsearch")
                                  ? id : "ytsearch1:" + id;
            case "soundcloud" -> id.startsWith("http") ? id : "scsearch1:" + id;
            default           -> id;
        };
    }

    private Path findActualFile(Path dir, String token) throws IOException {
        try (var stream = Files.list(dir)) {
            return stream
                    .filter(p -> p.getFileName().toString().startsWith(token))
                    .findFirst()
                    .orElse(null);
        }
    }

    private String detectMime(Path file) {
        String name = file.getFileName().toString().toLowerCase();
        if (name.endsWith(".mp3")) return "audio/mpeg";
        if (name.endsWith(".m4a")) return "audio/mp4";
        if (name.endsWith(".mp4")) return "video/mp4";
        if (name.endsWith(".webm")) return "audio/webm";
        if (name.endsWith(".ogg")) return "audio/ogg";
        return "application/octet-stream";
    }
}
