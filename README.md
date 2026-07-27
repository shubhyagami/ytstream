# ytstream

▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄
█  ╔═══════════════════════╗  █
█  ║  ▶ ytstream v1.0     ║  █
█  ╚═══════════════════════╝  █
▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen)](https://github.com/shubhyagami/ytstream/actions)
[![License](https://img.shields.io/badge/license-MIT-blue)](https://opensource.org/licenses/MIT)
[![Java Version](https://img.shields.io/badge/java-11%2B-orange)](https://www.java.com)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen)](CONTRIBUTING.md)

## Quick Start

Get up and running with `ytstream` in three simple steps:

1. **Clone the repository**
   ```bash
   git clone https://github.com/shubhyagami/ytstream.git
   cd ytstream
   ```

2. **Build the project** (requires Java 11+ and Maven)
   ```bash
   mvn clean package
   ```

3. **Run the tool**
   ```bash
   java -jar target/ytstream-1.0.0.jar --help
   ```

   For example, to stream a YouTube video by URL:
   ```bash
   java -jar target/ytstream-1.0.0.jar --url "https://youtu.be/dQw4w9WgXcQ"
   ```

## Pro Tips

- **Specify output format**: Use `--format` with `mp4`, `mp3`, or `webm` to control the downloaded file type.
- **Handle playlists efficiently**: Add `--playlist` to download every video in a playlist sequentially, with automatic rate limiting.
- **Stream without saving**: Pass `--stream-only` to play the video directly in your terminal (requires compatible media player).
- **Organize downloads**: Use `--output-dir` to set a custom folder for all output files.

## Weekly Highlight

**This Week's Feature: Playlist Streaming**  
Stream entire YouTube playlists with a single command. ytstream intelligently queues videos and handles rate limiting. Try it:

```bash
java -jar target/ytstream-1.0.0.jar --playlist "https://youtube.com/playlist?list=PL..."
```

## Changelog

### 2026-07-27 – v1.0.1
- Minor improvements to playlist streaming stability.
- Fixed progress bar display for large files (>2 GB).
- Added `--stream-only` flag for real-time playback without saving.
- Optimized memory usage during concurrent downloads.

### 2026-07-25 – v1.0.0
- Initial release of ytstream.
- Core streaming and downloading capabilities.
- Support for YouTube video URLs, playlists, and channels.
- Configurable output formats (MP4, MP3, WebM).
- Progress indicators and error handling.

## Quote of the Day

> "The best way to predict the future is to create it."  
> – Peter Drucker