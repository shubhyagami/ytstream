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
  --channel "https://youtube.com/@TechLectures" \
  --format mp3 \
  --output-dir ~/media/lectures \
  --playlist
```

**Why this works great:**
- 🎓 Sequential downloads preserve content order
- 💾 MP3 format keeps file sizes small for portable listening
- 📂 Organized output makes your archive instantly searchable
- ⚙️ Built-in rate limiting respects YouTube's API guidelines

## Weekly Highlight

**This Week's Feature: Playlist Streaming**  
Stream entire YouTube playlists with a single command. ytstream intelligently queues videos and handles r

---

## Time Variance Authority Changelog

As part of ongoing Temporal Engineering maintenance, `ytstream` is kept aligned with the Sacred Timeline. Each update is recorded for posterity.

### [2026-07-29]
- 🔀 **Feature**: Added `--branch-timeline` argument to isolate parallel download threads into alternate execution paths. This allows users to maintain multiple simultaneous playlist downloads without risking temporal collisions or splitting the root sacred timeline.
- ⚙️ **Enhancement**: Upgraded internal HTTP connection pool to handle up to 50 simultaneous streaming sessions dynamically.
- 🛡️ **Security**: Patched temporal vulnerability in metadata parsing (CVE-2026-771). This prevents the tool from recursively linking to YouTube videos uploaded before 2005, thereby preventing paradox loops.
- ⏱️ **Maintenance**: Purged the repository's entangled dead code branches and realigned the Maven dependencies to JDK 11 strict mode for optimal temporal stability.

---

## TVA Temporal Compliance Metrics

How does `ytstream` measure up against TVA temporal engineering standards for software reliability? 

| Metric | Rating | Description |
| :--- | :--- | :--- |
| **Timeline Stability** | 🌌 High | Zero recorded temporal branch splits during execution. |
| **Parallel Processing Safety** | ⚡ High | Thread loops successfully bypass the Dark Dimension boundary. |
| **Concurrency Limits** | 50 Ragnaroks | Hard limit for concurrent streams safely prevents multiversal collapse. |
| **Data Preservation Standard** | Secure | Chronologically stable file system partitioning for output. |

*Disclaimer: This tool is un-rated by the Time Variance Authority for multiversal use. Application of this software outside of it's intended timeline may result in a temporal pruning event.*

---