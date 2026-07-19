# ── Stage 1: Build ──────────────────────────────────────────────────────────
FROM gradle:8-jdk21 AS build
WORKDIR /app
COPY build.gradle.kts settings.gradle.kts gradle.properties ./
COPY src ./src
RUN gradle bootJar --no-daemon -x test

# ── Stage 2: Runtime ─────────────────────────────────────────────────────────
FROM eclipse-temurin:21-jre
WORKDIR /app

# Install yt-dlp, ffmpeg, python3
RUN apt-get update && apt-get install -y --no-install-recommends \
        python3 python3-pip ffmpeg wget ca-certificates && \
    wget -q https://github.com/yt-dlp/yt-dlp/releases/latest/download/yt-dlp \
         -O /usr/local/bin/yt-dlp && \
    chmod a+rx /usr/local/bin/yt-dlp && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

# Writable temp dir for stream cache
RUN mkdir -p /tmp/vidstream && chmod 777 /tmp/vidstream

COPY --from=build /app/build/libs/vidstream.jar app.jar

# Render injects PORT; default 10000
ENV PORT=10000
EXPOSE 10000

# JVM tuned for Render free tier (512 MB RAM)
ENTRYPOINT ["java", \
  "-Xmx384m", "-Xms128m", \
  "-XX:+UseG1GC", "-XX:MaxGCPauseMillis=100", \
  "-Djava.io.tmpdir=/tmp", \
  "-jar", "app.jar"]
