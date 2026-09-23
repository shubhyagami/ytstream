# ytstream

[![CI](https://img.shields.io/github/actions/workflow/status/shubhyagami/ytstream/ci.yml?branch=main&style=flat&logo=github)](https://github.com/shubhyagami/ytstream/actions)
[![License](https://img.shields.io/github/license/shubhyagami/ytstream?style=flat)](LICENSE)
[![Java](https://img.shields.io/badge/java-11%2B-orange.svg)](https://www.oracle.com/java/)
[![Latest Release](https://img.shields.io/github/v/release/shubhyagami/ytstream.svg?style=flat&logo=github)](https://github.com/shubhyagami/ytstream/releases)
[![PRs welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)](CONTRIBUTING.md)

**ytstream** is a lightweight Java CLI for downloading YouTube videos and converting them to MP3 audio or MP4 video. It accepts a single URL or a file containing multiple URLs, and supports options for output format, video quality, output location, dry-run previews, and verbose logging.

## Features

- Download a single YouTube video
- Batch download from a URL list file
- Export to MP3 or MP4
- Choose video quality (480p, 720p, 1080p; ignored for MP3)
- Preview planned downloads with `--dry-run`
- Set a custom output directory
- Enable verbose logging for troubleshooting

## Requirements

- Java 11 or newer (JDK)
- Maven 3.6 or newer (only needed to build from source)

## Getting Started

Clone the repository and build the JAR:

    git clone https://github.com/shubhyagami/ytstream.git
    cd ytstream
    mvn clean package

The build creates a JAR in `target/`, for example `target/ytstream-1.0.1.jar`. Verify it runs:

    java -jar target/ytstream-*.jar --help

You can also download the latest release JAR from the [Releases](https://github.com/shubhyagami/ytstream/releases) page.

## Usage

Run the JAR with one of the input options:

    java -jar target/ytstream-*.jar [options]

Run `--help` to see all available command-line options.

### Command-Line Options

| Option | Required | Type | Description |
|--------|----------|------|-------------|
| `--url <URL>` | Yes, or `--url-list` | String | A single YouTube video URL. |
| `--url-list <FILE>` | Yes, or `--url` | Path | Text file containing one URL per line. |
| `--output-format <mp3\|mp4>` | No | Enum | Target format. Default: `mp4`. |
| `--quality <480p\|720p\|1080p>` | No | Enum | Video resolution. Ignored for MP3. |
| `--output-dir <DIR>` | No | Path | Directory where downloads are saved. Default: current directory. |
| `--dry-run` | No | Flag | Log planned actions without downloading. |
| `--verbose` | No | Flag | Enable detailed debug logging. |
| `--help` | No | Flag | Display help and exit. |

> **Note:** Supply exactly one of `--url` or `--url-list`.

### Examples

Download a single video as MP3:

    java -jar target/ytstream-*.jar \
      --url https://youtu.be/dQw4w9WgXcQ \
      --output-format mp3 \
      --output-dir ~/Music

Batch download at 720p to a custom folder:

    java -jar target/ytstream-*.jar \
      --url-list playlist.txt \
      --output-format mp4 \
      --quality 720p \
      --output-dir ~/Downloads/yt-videos

Preview a batch download without downloading:

    java -jar target/ytstream-*.jar --url-list urls.txt --dry-run

## Contributing

Pull requests are welcome. Please read [CONTRIBUTING.md](CONTRIBUTING.md) and [CODE_OF_CONDUCT.md](CODE_OF_CONDUCT.md) before opening an issue or PR. Make sure unit tests pass and follow the existing code style.

## Changelog

**1.0.1 – 2026-08-05**

- Added `--dry-run` for previewing download queues.
- Improved error handling and rotation for expired YouTube URLs.

See [CHANGELOG.md](CHANGELOG.md) for the full history.

## License

Distributed under the MIT License. See [LICENSE](LICENSE) for details.
