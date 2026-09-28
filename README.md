# ytstream

[![CI](https://img.shields.io/github/actions/workflow/status/shubhyagami/ytstream/ci.yml?branch=main&style=flat&logo=github)](https://github.com/shubhyagami/ytstream/actions)
[![License](https://img.shields.io/github/license/shubhyagami/ytstream?style=flat)](LICENSE)
[![Java](https://img.shields.io/badge/java-11%2B-orange.svg)](https://www.oracle.com/java/)
[![Latest Release](https://img.shields.io/github/v/release/shubhyagami/ytstream.svg?style=flat&logo=github)](https://github.com/shubhyagami/ytstream/releases)
[![PRs welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)](CONTRIBUTING.md)

**ytstream** is a lightweight Java command-line tool that downloads YouTube videos and can save them as MP3 audio or MP4 video. It accepts a single video URL or a text file containing multiple URLs, with options for:

- output format (`mp3` or `mp4`)
- video quality (`480p`, `720p`, `1080p`; ignored for `mp3`)
- output directory
- dry-run preview
- verbose logging

> **Note:** This tool is intended for personal use. Please respect YouTube's Terms of Service and applicable copyright laws when downloading content.

---

## ✨ Features

- Download a single video or batch-download from a URL list file.
- Save output as MP3 (audio) or MP4 (video).
- Choose the video quality for MP4 output (480p, 720p, 1080p).
- Preview the download plan with `--dry-run` before committing.
- Write results to any directory you choose.
- Verbose mode for troubleshooting.

---

## 📦 Getting Started

### Requirements

- Java 11 or later
- Maven 3.6+ (only needed to build from source)

### Installation

**Pre-built binary**

Download the latest release JAR from the [Releases](https://github.com/shubhyagami/ytstream/releases) page.

**From source**

```bash
git clone https://github.com/shubhyagami/ytstream.git
cd ytstream
mvn clean package
```

This produces an executable JAR in `target/` (for example, `target/ytstream-1.0.1.jar`). Verify the installation with:

```bash
java -jar target/ytstream-*.jar --help
```

---

## 🚀 Usage

```bash
java -jar target/ytstream-*.jar [options]
```

### Command-Line Options

| Option | Required | Value | Description |
|--------|----------|-------|-------------|
| `--url <URL>` | Yes¹ | String | Single YouTube video URL. |
| `--url-list <FILE>` | Yes¹ | Path | Text file with one URL per line. |
| `--output-format <mp3\|mp4>` | No | Enum | Target format. Defaults to `mp4`. |
| `--quality <480p\|720p\|1080p>` | No | Enum | Desired video resolution. Ignored for `mp3`. |
| `--output-dir <DIR>` | No | Path | Destination directory. Defaults to the current directory. |
| `--dry-run` | No | Flag | Show planned actions without downloading. |
| `--verbose` | No | Flag | Enable detailed debug logging. |
| `--help` | No | Flag | Display help. |

¹ Exactly one of `--url` or `--url-list` must be provided.

### Examples

**Download a single video as MP3**

```bash
java -jar target/ytstream-*.jar \
  --url https://youtu.be/dQw4w9WgXcQ \
  --output-format mp3 \
  --output-dir ~/Music
```

**Batch download at 720p to a custom folder**

```bash
java -jar target/ytstream-*.jar \
  --url-list playlist.txt \
  --output-format mp4 \
  --quality 720p \
  --output-dir ~/Downloads/yt-videos
```

**Preview a batch download**

```bash
java -jar target/ytstream-*.jar \
  --url-list urls.txt \
  --dry-run
```

---

## 🤝 Contributing

Pull requests are welcome! Please read the [contributing guidelines](CONTRIBUTING.md) and [code of conduct](CODE_OF_CONDUCT.md) before opening an issue or pull request. Make sure all tests pass and that the existing code style is followed.

---

## 🗓️ Changelog

### 1.0.1 – 2026-08-05

- Added `--dry-run` for previewing the download queue.
- Improved error handling for expired YouTube URLs.

### 1.0.0 – Initial release

See the full history in [CHANGELOG.md](CHANGELOG.md).

---

## 📄 License

Distributed under the [MIT License](LICENSE).
