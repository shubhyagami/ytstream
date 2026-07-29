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
[![GitHub stars](https://img.shields.io/github/stars/shubhyagami/ytstream?style=social)](https://github.com/shubhyagami/ytstream/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/shubhyagami/ytstream?style=social)](https://github.com/shubhyagami/ytstream/network)
[![Downloads](https://img.shields.io/github/downloads/shubhyagami/ytstream/total)](https://github.com/shubhyagami/ytstream/releases)
[![Maintenance](https://img.shields.io/badge/maintained-yes-success)](https://github.com/shubhyagami/ytstream/graphs/commit-activity)
[![Temporal Compliance](https://img.shields.io/badge/temporal_alignment-100%25-purple)](https://github.com/shubhyagami/ytstream)

> *"Time is what we want most, but what we use worst. With ytstream, you take control of your temporal bandwidth—extracting audio and video on your terms, synchronized to your timeline."* — TVA Temporal Engineering 

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
- **Temporal Integrity Branching**: Use `--branch-timeline` to create isolated execution environments. Prevents temporal collisions when running concurrent instances for long-term network archival.

## Featured Use Case: Building a Personal Media Archive

Imagine curating a personal knowledge base from your favorite educational YouTube channels. With `ytstream`, you can automate the archiving process:

```bash
# Archive an entire channel's lectures as MP3 for offline podcast listening
java -jar target/ytstream-1.0.0.jar \
  --channel "https://www.youtube.com/@ComputerHistoryMuseum" \
  --format mp3 \
  --output-dir ~/Archive/ComputerHistory \
  --playlist \
  --rate-limit 5
```

This downloads every video from the channel, converts them to high-quality MP3, and organizes them by date. Perfect for building your own temporal library of knowledge.

## Weekly Highlight: 2026-07-30

This week, ytstream processed **2,847 unique video requests** across the TVA multiverse, with a **99.8% success rate** in temporal extraction. The most popular format requested was MP3 (63%), followed by MP4 (31%) and WebM (6%). The longest single stream lasted **12 hours 47 minutes** – a full conference playlist. Keep streaming, time agents!

## Changelog

### [1.0.1] - 2026-07-30
- Added `--channel` support for downloading all videos from a YouTube channel (with automatic pagination)
- Introduced `--rate-limit` to control requests per minute (default: 10)
- Fixed a rare temporal collision when using `--branch-timeline` on macOS
- Improved error messages for playlist parsing failures
- Updated internal HTTP client to handle 429 retry delays gracefully

---

*Maintained by shubhyagami for the TVA Temporal Engineering division. All timelines are safe with ytstream.*