# ytstream

Download YouTube videos and convert them to **MP3** (audio‑only) or **MP4** (video‑audio) from the command line.

![CI](https://img.shields.io/github/actions/workflow/status/shubhyagami/ytstream/ci.yml?branch=main&style=flat&logo=github)
![License](https://img.shields.io/github/license/shubhyagami/ytstream?style=flat)
![Java](https://img.shields.io/badge/java-11%2B-orange.svg)
![Contributions welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)

---

## Table of contents

| Section | Purpose |
|---------|---------|
| [Overview](#overview) | What the tool does |
| [Features](#features) | Core capabilities |
| [Prerequisites](#prerequisites) | What you need |
| [Installation](#installation) | Get it running |
| [Quick start](#quick-start) | One‑liner usage |
| [Command‑line options](#command-line-options) | Full list of flags |
| [Examples](#examples) | Real‑world scenarios |
| [Contributing](#contributing) | How to help |
| [Changelog](#changelog) | Release notes |
| [License](#license) | Legal |

---

## Overview

`ytstream` is a lightweight, pure‑Java utility that uses the `youtube-dl` library to extract media from YouTube.  
It supports:

* Single‑video downloads or batch downloads from a text file.
* Conversion to MP3 or MP4.
* Selecting MP4 resolution (480p, 720p, 1080p).
* Previewing actions with `--dry-run`.
* Custom output directories and verbose logging.

---

## Features

| Feature | Description |
|---------|-------------|
| **Batch downloads** | Provide a file of URLs (`--url-list`) to download many videos at once. |
| **Format conversion** | `--output-format mp3` (audio‑only) or `--output-format mp4` (video‑audio). |
| **Resolution control** | `--quality 480p|720p|1080p` for MP4 downloads. |
| **Dry‑run** | `--dry-run` shows what would happen without performing downloads. |
| **Custom output directory** | `--output-dir <path>` (defaults to the current working directory). |
| **Verbose logging** | `--verbose` prints detailed progress information. |
| **Help** | `--help` displays usage information. |

---

## Prerequisites

* Java 11 or newer (OpenJDK, Temurin, etc.)
* Maven 3.6+ (only for building from source)

---

## Installation

```bash
git clone https://github.com/shubhyagami/ytstream.git
cd ytstream
mvn clean package
```

The executable JAR is located in `target/` (e.g. `ytstream-1.0.0.jar`).  
No additional runtime dependencies are required beyond the JDK.

---

## Quick start

```bash
# Show help
java -jar target/ytstream-1.0.0.jar --help

# Download a single video as MP3
java -jar target/ytstream-1.0.0.jar \
  --url https://youtu.be/xyz123 \
  --output-format mp3

# Batch download from a file, storing results in ./downloads
java -jar target/ytstream-1.0.0.jar \
  --url-list urls.txt \
  --output-dir ./downloads

# Preview a command without downloading
java -jar target/ytstream-1.0.0.jar \
  --url https://youtu.be/xyz123 \
  --dry-run
```

---

## Command‑line options

| Option | Required | Description |
|--------|----------|-------------|
| `--url <URL>` | Yes (unless `--url-list` is supplied) | A single YouTube video URL. |
| `--url-list <FILE>` | No | Path to a text file with one YouTube URL per line. |
| `--output-format <mp3|mp4>` | No | Target format (`mp3` for audio, `mp4` for video‑audio). Defaults to `mp4`. |
| `--quality <480p|720p|1080p>` | No | Force a specific MP4 resolution for video downloads. |
| `--output-dir <PATH>` | No | Directory where downloaded files will be stored. |
| `--dry-run` | No | Show the actions that would be performed without downloading. |
| `--verbose` | No | Emit detailed logs for each step. |
| `--help` | No | Display this help message. |

---

## Examples

```bash
# Convert a single video to MP3 and save to ~/Music
java -jar target/ytstream-1.0.0.jar \
  --url https://youtu.be/dQw4w9WgXcQ \
  --output-format mp3 \
  --output-dir ~/Music

# Download a playlist (one URL per line) at 720p
java -jar target/ytstream-1.0.0.jar \
  --url-list playlist.txt \
  --output-format mp4 \
  --quality 720p \
  --output-dir ~/Downloads/playlist
```

---

## Contributing

Pull requests are always welcome!  
Please see the [CONTRIBUTING.md](CONTRIBUTING.md) and [CODE_OF_CONDUCT.md](CODE_OF_CONDUCT.md) documents for guidelines.

---

## Changelog

**v1.0.1 – 2026‑08‑05**

- Added `--dry-run` flag for previewing operations.
- Improved handling of expired YouTube URLs.

Full history is in the [CHANGELOG.md](CHANGELOG.md).

---

## License

MIT – see the [LICENSE](LICENSE) file.
