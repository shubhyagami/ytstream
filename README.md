# ytstream

A lightweight, command‑line utility for downloading and converting YouTube videos to **MP3** (audio‑only) or **MP4** (video‑audio).

![CI](https://img.shields.io/github/actions/workflow/status/shubhyagami/ytstream/ci.yml?branch=main&style=flat)
![License](https://img.shields.io/github/license/shubhyagami/ytstream?style=flat)
![Java](https://img.shields.io/badge/java-11%2B-orange.svg)
![Contributions welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)

## Overview

`ytstream` downloads a YouTube video, optionally converts it to the desired format, and saves it to a directory you choose. The tool is written in Java and uses the popular youtube‑dl library under the hood.

## Features

- Download a single video or batch‑download from a text file.
- Convert to `mp3` (audio‑only) or `mp4` (video‑audio).
- Choose video quality for MP4 downloads (`480p`, `720p`, `1080p`).
- Preview actions with `--dry-run` before any network traffic.
- Specify an output directory; defaults to the current working directory.
- Verbose logging for debugging.

## Prerequisites

- Java 11 or newer (OpenJDK, Temurin, etc.)
- Maven 3.6+

## Getting Started

Clone the repository and build the executable JAR:

```bash
git clone https://github.com/shubhyagami/ytstream.git
cd ytstream
mvn clean package
```

The JAR is located in `target/` (e.g., `ytstream-1.0.0.jar`).

Run the tool with the options shown in the help text:

```bash
java -jar target/ytstream-1.0.0.jar --help
```

### Quick examples

```bash
# Download a single video as MP3
java -jar target/ytstream-1.0.0.jar --url https://youtu.be/xyz123 --output-format mp3

# Batch download from a file, storing results in ./downloads
java -jar target/ytstream-1.0.0.jar --url-list urls.txt --output-dir ./downloads

# Preview a command without downloading
java -jar target/ytstream-1.0.0.jar --url https://youtu.be/xyz123 --dry-run
```

## Usage

| Option | Required | Description |
|--------|----------|-------------|
| `--url <URL>` | Yes (unless `--url-list` is used) | Single YouTube video URL. |
| `--url-list <FILE>` | No | Text file containing one YouTube URL per line. |
| `--output-format <mp3|mp4>` | No | Target format (`mp3` for audio‑only, `mp4` for video‑audio). Defaults to `mp4`. |
| `--quality <480p|720p|1080p>` | No | Force a specific resolution for MP4 downloads. |
| `--output-dir <PATH>` | No | Directory where downloaded files will be stored. |
| `--dry-run` | No | Print the commands that would be executed without downloading. |
| `--verbose` | No | Emit detailed logs for each step. |
| `--help` | No | Show this help message. |

## Contributing

Pull requests are welcome! Before opening an issue or PR, please read:

- [CONTRIBUTING.md](CONTRIBUTING.md)
- [CODE_OF_CONDUCT.md](CODE_OF_CONDUCT.md)

## Changelog

**v1.0.1 – 2026‑08‑05**

- Added `--dry-run` flag for previewing operations.
- Improved handling of expired YouTube URLs.

See the full history in [CHANGELOG.md](CHANGELOG.md).

## License

MIT – see the [LICENSE](LICENSE) file.
