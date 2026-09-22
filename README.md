# ytstream

[![CI](https://img.shields.io/github/actions/workflow/status/shubhyagami/ytstream/ci.yml?branch=main&style=flat&logo=github)](https://github.com/shubhyagami/ytstream/actions)
[![License](https://img.shields.io/github/license/shubhyagami/ytstream?style=flat)](LICENSE)
[![Java](https://img.shields.io/badge/java-11%2B-orange.svg)](https://www.oracle.com/java/)
[![PRs welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)](CONTRIBUTING.md)
[![Latest Release](https://img.shields.io/github/v/release/shubhyagami/ytstream.svg?style=flat&logo=github)](https://github.com/shubhyagami/ytstream/releases)

**ytstream** is a lightweight Java‑based CLI tool that lets you download YouTube videos and convert them to MP3 (audio) or MP4 (video).  
It supports single URLs, text files with multiple URLs, and playlist files, and provides options for quality, output location, dry‑run, and verbose logging.

---

## Features

- Download a single YouTube video
- Batch download from a file or playlist
- Export to MP3 or MP4
- Choose video quality (480p, 720p, 1080p) – ignored for MP3
- Dry‑run mode to preview the download queue
- Custom output directory
- Verbose logging for troubleshooting

---

## Prerequisites

- **Java 11 +** (JDK 11 or newer)
- **Maven 3.6 +** (to build from source)

---

## Quick Start

```bash
git clone https://github.com/shubhyagami/ytstream.git
cd ytstream
mvn clean package
```

The JAR will be in `target/` (e.g. `target/ytstream-1.0.1.jar`).  
Run it with:

```bash
java -jar target/ytstream-*.jar --help
```

Alternatively, download the latest release JAR from the **Releases** page.

---

## Usage

```bash
java -jar target/ytstream-*.jar [options]
```

Run `--help` to list all available command‑line options.

### Command‑Line Options

| Option | Required | Type | Description |
|--------|----------|------|-------------|
| `--url <URL>` | Yes **or** `--url-list` | String | A single YouTube video URL. |
| `--url-list <FILE>` | No | Path | Text file containing one URL per line. |
| `--output-format <mp3|mp4>` | No | Enum | Target format. Default: `mp4`. |
| `--quality <480p|720p|1080p>` | No | Enum | Video resolution (ignored for MP3). |
| `--output-dir <DIR>` | No | Path | Where to save downloaded files. Default: current directory. |
| `--dry-run` | No | Flag | Log planned actions without downloading. |
| `--verbose` | No | Flag | Enable detailed debug logging. |
| `--help` | No | Flag | Display help and exit. |

> **Note:** Exactly one of `--url` or `--url-list` must be supplied.

### Examples

#### 1. Download a single video as MP3

```bash
java -jar target/ytstream-*.jar \
  --url https://youtu.be/dQw4w9WgXcQ \
  --output-format mp3 \
  --output-dir ~/Music
```

#### 2. Batch download at 720p to a custom folder

```bash
java -jar target/ytstream-*.jar \
  --url-list playlist.txt \
  --output-format mp4 \
  --quality 720p \
  --output-dir ~/Downloads/yt-videos
```

#### 3. Preview a batch download (dry‑run)

```bash
java -jar target/ytstream-*.jar --url-list urls.txt --dry-run
```

---

## Contributing

Pull requests are welcome.  
Please read the [CONTRIBUTING.md](CONTRIBUTING.md) and [CODE_OF_CONDUCT.md](CODE_OF_CONDUCT.md) guidelines before submitting an issue or PR.  
Ensure that unit tests pass and code style is followed.

---

## Changelog

**1.0.1 – 2026‑08‑05**

- Added `--dry-run` flag for previewing download queues.
- Improved error handling and rotation for expired YouTube URLs.

See the full history in [CHANGELOG.md](CHANGELOG.md).

---

## License

Distributed under the MIT License. See [LICENSE](LICENSE) for details.
