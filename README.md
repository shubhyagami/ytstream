# ytstream

A lightweight command‑line utility that extracts and streams audio or video from YouTube.

[![CI](https://img.shields.io/github/actions/workflow/status/shubhyagami/ytstream/ci.yml?branch=main&style=flat)](https://github.com/shubhyagami/ytstream/actions)
[![License](https://img.shields.io/github/license/shubhyagami/ytstream.svg?style=flat)](LICENSE)
[![Java](https://img.shields.io/badge/java-11%2B-orange.svg)](https://adoptium.net/temurin11/)
[![Contributions welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)](https://github.com/shubhyagami/ytstream/pulls)

---

## Features

- Convert a YouTube video to **MP3** (audio‑only) or **MP4** (audio‑video).
- Specify a fixed resolution: `480p`, `720p`, or `1080p`.
- Process a single URL or a batch file containing many URLs.
- Preview downloads with the `--dry-run` flag.
- Define a custom output directory.
- Verbose logging for detailed debugging.

---

## Getting Started

### Prerequisites

- **Java 11+** (OpenJDK, Temurin, etc.).
- **Maven 3.6+**.

### Build the project

```bash
git clone https://github.com/shubhyagami/ytstream.git
cd ytstream
mvn clean package
```

The executable JAR is written to `target/`.

### Quick examples

```bash
# Single audio download
java -jar target/ytstream-1.0.0.jar --url https://youtu.be/xyz123 --output-format mp3

# Batch download from a file
java -jar target/ytstream-1.0.0.jar --url-list urls.txt --output-dir ./downloads
```

For a full list of options, run:

```bash
java -jar target/ytstream-1.0.0.jar --help
```

---

## Usage

| Option | Description |
|--------|-------------|
| `--url <URL>` | YouTube video URL (required for single‑video mode). |
| `--url-list <FILE>` | File containing one YouTube URL per line. |
| `--output-format <mp3|mp4>` | Choose `mp3` (audio‑only) or `mp4` (audio‑video). Default: `mp4`. |
| `--quality <480p|720p|1080p>` | Force a specific resolution. |
| `--output-dir <PATH>` | Where to save downloaded files. |
| `--dry-run` | Print the plan without accessing the internet. |
| `--verbose` | Emit detailed logs for each step. |

---

## Configuration

All settings are supplied via command‑line arguments. No external configuration files are required.

---

## Contributing

Feel free to open issues or submit pull requests. Please read the following first:

- [CONTRIBUTING.md](CONTRIBUTING.md)
- [CODE_OF_CONDUCT.md](CODE_OF_CONDUCT.md)

---

## Changelog

**v1.0.1 – 2026‑08‑05**

- Added `--dry-run` flag for safe preview of download operations.
- Improved error handling for expired YouTube URLs.

See the full history in [CHANGELOG.md](CHANGELOG.md).

---

## License

MIT – see the [LICENSE](LICENSE) file.
