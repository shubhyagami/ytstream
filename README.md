# ytstream

A lightweight command‑line tool that downloads YouTube videos and can instantly convert them into **MP3** (audio‑only) or **MP4** (video‑audio).

![CI](https://img.shields.io/github/actions/workflow/status/shubhyagami/ytstream/ci.yml?branch=main&style=flat)
![License](https://img.shields.io/github/license/shubhyagami/ytstream?style=flat)
![Java](https://img.shields.io/badge/java-11%2B-orange.svg)
![Contributions welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)

---

## Table of contents

- [Overview](#overview)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Getting started](#getting-started)
- [Command‑line options](#command-line-options)
- [Examples](#examples)
- [Contributing](#contributing)
- [Changelog](#changelog)
- [License](#license)

---

## Overview

`ytstream` fetches a YouTube video, optionally converts it to the desired format, and writes the file to a directory chosen by the user.  
The tool is written in pure Java and uses the `youtube-dl` library for media extraction.

---

## Features

* Download a single video or a batch from a text file.
* Export to MP3 (audio‑only) or MP4 (video‑audio).
* Select MP4 resolution (`480p`, `720p`, `1080p`).
* Preview scheduled actions with `--dry-run`.
* Store downloads in a custom output directory (defaults to the current working directory).
* Verbose logging for debugging.

---

## Prerequisites

* Java 11 or newer (OpenJDK, Temurin, etc.)
* Maven 3.6+

---

## Installation

```bash
git clone https://github.com/shubhyagami/ytstream.git
cd ytstream
mvn clean package
```

The executable JAR is produced in the `target/` directory (e.g. `ytstream-1.0.0.jar`). No additional runtime dependencies are required beyond the JDK.

---

## Getting started

```bash
# Show help
java -jar target/ytstream-1.0.0.jar --help
```

### One‑liner examples

```bash
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
| `--url <URL>` | Yes (unless `--url-list` is provided) | A single YouTube video URL. |
| `--url-list <FILE>` | No | Path to a text file containing one YouTube URL per line. |
| `--output-format <mp3|mp4>` | No | Target format (`mp3` for audio, `mp4` for video‑audio). Defaults to `mp4`. |
| `--quality <480p|720p|1080p>` | No | Force a specific resolution for MP4 downloads. |
| `--output-dir <PATH>` | No | Directory where downloaded files will be stored. |
| `--dry-run` | No | Print the actions that would be performed without downloading. |
| `--verbose` | No | Emit detailed logs for each step. |
| `--help` | No | Show this help message. |

---

## Examples

```bash
# Convert a video to MP3
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

Pull requests are welcome. Please read the contribution guidelines:

* [CONTRIBUTING.md](CONTRIBUTING.md)
* [CODE_OF_CONDUCT.md](CODE_OF_CONDUCT.md)

---

## Changelog

**v1.0.1 – 2026‑08‑05**

* Added `--dry-run` flag for previewing operations.
* Improved handling of expired YouTube URLs.

Full history is in [CHANGELOG.md](CHANGELOG.md).

---

## License

MIT – see the [LICENSE](LICENSE) file.
