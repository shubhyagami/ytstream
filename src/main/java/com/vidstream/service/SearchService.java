package com.vidstream.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchService {

    private static final Logger log = LoggerFactory.getLogger(SearchService.class);
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final HttpClient http = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .followRedirects(HttpClient.Redirect.ALWAYS)
            .build();

    private static final String YT_INNERTUBE_API_KEY = "AIzaSyAO_FJ2SlqU8Q4STEHLGCilw_Y9_11qcW8";

    @Value("${vidstream.rapidapi.key}")
    private String rapidApiKey;

    @Value("${vidstream.ytdlp.path:yt-dlp}")
    private String ytdlpPath;

    /** Unified result record for all sources */
    public record MediaResult(
        String id,
        String title,
        String artist,
        String channel,
        String thumbnail,
        long   durationSeconds,
        String source,   // youtube | spotify | soundcloud
        String type      // video | audio
    ) {}

    public List<MediaResult> search(String query, String source, int limit) throws Exception {
        return switch (source.toLowerCase()) {
            case "spotify"    -> searchSpotify(query, limit);
            case "soundcloud" -> searchSoundCloud(query, limit);
            default           -> searchYouTube(query, limit);
        };
    }

    // ── YouTube via Innertube API ─────────────────────────────────────────────

    @SuppressWarnings("unchecked")
    private List<MediaResult> searchYouTube(String query, int limit) throws IOException, InterruptedException {
        String body = """
            {"context":{"client":{"clientName":"WEB","clientVersion":"2.20231219.04.00",
            "hl":"en","gl":"US"}},"query":"%s","params":"EgIQAQ=="}
            """.formatted(query.replace("\"", "\\\""));

        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create("https://www.youtube.com/youtubei/v1/search?key=" + YT_INNERTUBE_API_KEY))
            .header("Content-Type", "application/json")
            .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
            .header("X-YouTube-Client-Name", "1")
            .header("X-YouTube-Client-Version", "2.20231219.04.00")
            .timeout(Duration.ofSeconds(15))
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .build();

        HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
        if (resp.statusCode() != 200) {
            log.warn("YouTube Innertube returned {}", resp.statusCode());
            return fallbackYouTubeRapidApi(query, limit);
        }

        List<MediaResult> results = new ArrayList<>();
        try {
            Map<String, Object> json = mapper.readValue(resp.body(), new TypeReference<>() {});
            Object contents = deepGet(json, "contents", "twoColumnSearchResultsRenderer",
                    "primaryContents", "sectionListRenderer", "contents");
            if (!(contents instanceof List)) return fallbackYouTubeRapidApi(query, limit);

            for (Object section : (List<?>) contents) {
                if (!(section instanceof Map)) continue;
                Object items = deepGet((Map<?, ?>) section, "itemSectionRenderer", "contents");
                if (!(items instanceof List)) continue;
                for (Object item : (List<?>) items) {
                    if (!(item instanceof Map)) continue;
                    Map<?, ?> vr = (Map<?, ?>) ((Map<?, ?>) item).get("videoRenderer");
                    if (vr == null) continue;

                    String videoId = (String) vr.get("videoId");
                    String title   = getText(vr.get("title"));
                    String channel = getText(deepGet((Map<?, ?>) vr, "ownerText"));
                    String thumb   = getBestThumb(vr.get("thumbnail"));
                    long   dur     = parseDuration(getText(deepGet((Map<?, ?>) vr, "lengthText")));

                    if (videoId != null && title != null) {
                        results.add(new MediaResult(videoId, title, channel, channel,
                                thumb, dur, "youtube", "video"));
                    }
                    if (results.size() >= limit) break;
                }
                if (results.size() >= limit) break;
            }
        } catch (Exception e) {
            log.warn("YouTube parse error: {}", e.getMessage());
            return fallbackYouTubeRapidApi(query, limit);
        }

        return results.isEmpty() ? fallbackYouTubeRapidApi(query, limit) : results;
    }

    @SuppressWarnings("unchecked")
    private List<MediaResult> fallbackYouTubeRapidApi(String query, int limit) throws IOException, InterruptedException {
        String url = "https://youtube138.p.rapidapi.com/search/?q="
                + URLEncoder.encode(query, StandardCharsets.UTF_8) + "&hl=en&gl=US";
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("x-rapidapi-host", "youtube138.p.rapidapi.com")
            .header("x-rapidapi-key", rapidApiKey)
            .timeout(Duration.ofSeconds(15))
            .GET().build();
        HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
        List<MediaResult> results = new ArrayList<>();
        if (resp.statusCode() != 200) return results;
        try {
            Map<String, Object> json = mapper.readValue(resp.body(), new TypeReference<>() {});
            List<?> contents = (List<?>) json.get("contents");
            if (contents == null) return results;
            for (Object item : contents) {
                if (!(item instanceof Map)) continue;
                Map<?, ?> vr = (Map<?, ?>) ((Map<?, ?>) item).get("video");
                if (vr == null) continue;
                String videoId = (String) vr.get("videoId");
                String title   = (String) vr.get("title");
                String channel = "";
                Object authorObj = vr.get("author");
                if (authorObj instanceof Map) channel = (String) ((Map<?, ?>) authorObj).get("title");
                String thumb = "";
                Object thumbObj = vr.get("thumbnails");
                if (thumbObj instanceof List<?> tl && !tl.isEmpty() && tl.get(0) instanceof Map<?, ?> tm) {
                    thumb = (String) tm.get("url");
                }
                Number dur = (Number) vr.get("lengthSeconds");
                results.add(new MediaResult(videoId, title != null ? title : "",
                        channel, channel, thumb, dur != null ? dur.longValue() : 0,
                        "youtube", "video"));
                if (results.size() >= limit) break;
            }
        } catch (Exception e) {
            log.warn("RapidAPI YouTube parse error: {}", e.getMessage());
        }
        return results;
    }

    // ── Spotify via RapidAPI ──────────────────────────────────────────────────

    @SuppressWarnings("unchecked")
    private List<MediaResult> searchSpotify(String query, int limit) throws IOException, InterruptedException {
        String url = "https://spotify81.p.rapidapi.com/search?q="
                + URLEncoder.encode(query, StandardCharsets.UTF_8)
                + "&type=tracks&offset=0&limit=" + limit + "&numberOfTopResults=5";
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("x-rapidapi-host", "spotify81.p.rapidapi.com")
            .header("x-rapidapi-key", rapidApiKey)
            .timeout(Duration.ofSeconds(12))
            .GET().build();
        HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
        List<MediaResult> results = new ArrayList<>();
        if (resp.statusCode() != 200) {
            log.warn("Spotify RapidAPI returned {}, falling back to YouTube", resp.statusCode());
            return searchYouTube(query, limit);
        }
        try {
            Map<String, Object> json = mapper.readValue(resp.body(), new TypeReference<>() {});
            Object tracksObj = deepGet(json, "tracks", "items");
            if (!(tracksObj instanceof List)) return searchYouTube(query, limit);
            for (Object item : (List<?>) tracksObj) {
                if (!(item instanceof Map)) continue;
                Map<?, ?> data = (Map<?, ?>) ((Map<?, ?>) item).get("data");
                if (data == null) continue;
                String id    = (String) deepGet(data, "uri");
                String title = getText(deepGet(data, "name"));

                // artist
                String artist = "";
                Object artistsObj = deepGet(data, "artists", "items");
                if (artistsObj instanceof List<?> al && !al.isEmpty() && al.get(0) instanceof Map<?, ?> am) {
                    artist = getText(((Map<?, ?>) am).get("profile"));
                    if (artist == null || artist.isBlank()) artist = getText(am.get("name"));
                }

                // thumbnail
                String thumb = "";
                Object albumArt = deepGet(data, "albumOfTrack", "coverArt", "sources");
                if (albumArt instanceof List<?> sl && !sl.isEmpty() && sl.get(0) instanceof Map<?, ?> sm) {
                    thumb = (String) sm.get("url");
                }

                // duration
                Number durMs = (Number) deepGet(data, "duration", "totalMilliseconds");
                long durSec = durMs != null ? durMs.longValue() / 1000 : 0;

                if (id != null && title != null) {
                    // Use Spotify URI as ID so yt-dlp can resolve it; fallback: search by title+artist
                    String searchId = id.startsWith("spotify:") ? id : "ytsearch1:" + title + " " + artist;
                    results.add(new MediaResult(searchId, title, artist, artist, thumb, durSec,
                            "spotify", "audio"));
                }
                if (results.size() >= limit) break;
            }
        } catch (Exception e) {
            log.warn("Spotify parse error: {}", e.getMessage());
            return searchYouTube(query, limit);
        }
        return results.isEmpty() ? searchYouTube(query, limit) : results;
    }

    // ── SoundCloud via yt-dlp metadata ───────────────────────────────────────

    private List<MediaResult> searchSoundCloud(String query, int limit) {
        List<MediaResult> results = new ArrayList<>();
        try {
            ProcessBuilder pb = new ProcessBuilder(
                ytdlpPath,
                "scsearch" + limit + ":" + query,
                "--dump-json", "--no-playlist",
                "--default-search", "scsearch",
                "--flat-playlist"
            );
            pb.redirectErrorStream(true);
            Process proc = pb.start();
            String output = new String(proc.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            proc.waitFor();

            for (String line : output.split("\n")) {
                line = line.trim();
                if (line.isBlank() || !line.startsWith("{")) continue;
                try {
                    Map<String, Object> j = mapper.readValue(line, new TypeReference<>() {});
                    String id       = (String) j.get("id");
                    String webUrl   = (String) j.getOrDefault("webpage_url", "");
                    String title    = (String) j.getOrDefault("title", "Unknown");
                    String uploader = (String) j.getOrDefault("uploader", "");
                    String thumb    = (String) j.getOrDefault("thumbnail", "");
                    Number dur      = (Number) j.get("duration");
                    String resolveId = webUrl.isBlank() ? id : webUrl;
                    results.add(new MediaResult(resolveId, title, uploader, uploader,
                            thumb, dur != null ? dur.longValue() : 0, "soundcloud", "audio"));
                } catch (Exception ignored) {}
                if (results.size() >= limit) break;
            }
        } catch (Exception e) {
            log.error("SoundCloud yt-dlp search error: {}", e.getMessage());
        }
        return results;
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    @SuppressWarnings("unchecked")
    private Object deepGet(Object obj, String... keys) {
        Object cur = obj;
        for (String key : keys) {
            if (!(cur instanceof Map)) return null;
            cur = ((Map<?, ?>) cur).get(key);
        }
        return cur;
    }

    @SuppressWarnings("unchecked")
    private String getText(Object obj) {
        if (obj instanceof String s) return s;
        if (!(obj instanceof Map)) return "";
        Map<?, ?> m = (Map<?, ?>) obj;
        if (m.containsKey("simpleText")) return (String) m.get("simpleText");
        Object runs = m.get("runs");
        if (runs instanceof List<?> rl && !rl.isEmpty() && rl.get(0) instanceof Map<?, ?> rm) {
            return (String) rm.get("text");
        }
        if (m.containsKey("name")) return (String) m.get("name");
        return "";
    }

    @SuppressWarnings("unchecked")
    private String getBestThumb(Object thumbObj) {
        if (!(thumbObj instanceof Map)) return "";
        Object thumbs = ((Map<?, ?>) thumbObj).get("thumbnails");
        if (!(thumbs instanceof List<?> tl) || tl.isEmpty()) return "";
        // Pick highest quality
        Object last = tl.get(tl.size() - 1);
        if (last instanceof Map<?, ?> lm) return (String) lm.get("url");
        return "";
    }

    private long parseDuration(String text) {
        if (text == null || text.isBlank()) return 0;
        String[] parts = text.split(":");
        try {
            if (parts.length == 2) return Long.parseLong(parts[0]) * 60 + Long.parseLong(parts[1]);
            if (parts.length == 3) return Long.parseLong(parts[0]) * 3600
                    + Long.parseLong(parts[1]) * 60 + Long.parseLong(parts[2]);
        } catch (NumberFormatException ignored) {}
        return 0;
    }
}
