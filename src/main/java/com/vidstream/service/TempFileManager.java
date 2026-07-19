package com.vidstream.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TempFileManager {

    private static final Logger log = LoggerFactory.getLogger(TempFileManager.class);
    private static final long TTL_MS = 10 * 60 * 1000L; // 10 minutes

    private record Entry(Path path, String mimeType, Instant createdAt, long size) {}

    private final Map<String, Entry> registry = new ConcurrentHashMap<>();

    public void register(String token, Path path, String mimeType) {
        long size = 0;
        try { size = Files.size(path); } catch (IOException ignored) {}
        registry.put(token, new Entry(path, mimeType, Instant.now(), size));
    }

    public StreamService.StreamInfo get(String token) {
        Entry e = registry.get(token);
        if (e == null || !Files.exists(e.path())) return null;
        return new StreamService.StreamInfo(e.path(), e.size(), e.mimeType());
    }

    public void evict(String token) {
        Entry e = registry.remove(token);
        if (e != null) deleteQuietly(e.path());
    }

    @Scheduled(fixedDelay = 60_000)
    public void cleanup() {
        Instant cutoff = Instant.now().minusMillis(TTL_MS);
        int count = 0;
        for (var entry : registry.entrySet()) {
            if (entry.getValue().createdAt().isBefore(cutoff)) {
                evict(entry.getKey());
                count++;
            }
        }
        if (count > 0) log.info("Cleaned up {} expired temp streams", count);
    }

    private void deleteQuietly(Path path) {
        try { Files.deleteIfExists(path); } catch (IOException ignored) {}
    }
}
