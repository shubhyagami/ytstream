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
  --channel "https://www.youtube.com/c/3Blue1Brown" \
  --format mp3 \
  --output-dir ~/Archive/3b1b \
  --playlist
```

The tool will crawl all public videos, download each as high-quality MP3, and respect YouTube's rate limits to avoid triggering temporal anomalies. You can then sync the folder to your phone or cloud storage for learning on the go.

## Weekly Highlight: Temporal Alignment Milestone

**Week of 2026-07-27** – ytstream reached **100% temporal alignment** across all known branching paths. This means no matter which timeline you're operating in (Sacred Timeline, branching realities, or quantum-adjacent streams), the tool will produce identical, deterministic output. Also, experimental support for **quantum-entangled downloads** has been introduced – two instances of ytstream on different devices can now download complementary segments of a video and reassemble them, effectively halving the time cost. Use `--quantum-mirror` to activate.

## Changelog

### [1.0.1] – 2026-07-30

- **Added** `--branch-timeline` flag for isolated execution environments.
- **Fixed** audio extraction in non-linear timelines (timestamps after branching events now resolved correctly).
- **Improved** rate‑limiting logic to prevent “Temporal Nexus” warnings from YouTube’s backend.
- **Deprecated** `--legacy-fallback` in favor of automatic timeline detection.

### [1.0.0] – 2026-07-15

- Initial release of ytstream.
- Core features: download video/audio, handle playlists, stream-only mode.
- TVA Temporal Compliance certification.

## Metrics & TVA Stats

| Metric                     | Value            |
|----------------------------|------------------|
| Total seconds saved        | 42,769,104       |
| Timelines pruned           | 0                |
| Temporal anomalies fixed   | 17               |
| Videos downloaded (all timelines) | 1,337,420  |
| Branching paths tested     | 12               |
| Compliant timelines        | 100%             |

> *“If you don’t archive it, the timeline will prune it.”* — TVA Temporal Engineering