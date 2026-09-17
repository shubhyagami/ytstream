[K[2m  [2mmodel openai/gpt-oss-20b failed, trying next...[0m[0m
[K[2m  [2mmodel openai/gpt-oss-120b failed, trying next...[0m[0m
# ytstream

[![CI](https://img.shields.io/github/actions/workflow/status/shubhyagami/ytstream/ci.yml?branch=main&style=flat&logo=github)](https://github.com/shubhyagami/ytstream/actions)
[![License](https://img.shields.io/github/license/shubhyagami/ytstream?style=flat)](LICENSE)
[![Java](https://img.shields.io/badge/java-11%2B-orange.svg)](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
[![PRs welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)](CONTRIBUTING.md)

A lightweight, Java-based CLI tool for downloading YouTube videos and converting them to **MP3** (audio) or **MP4** (video). It supports single URLs, playlist files, and batch processing with customizable quality and output settings.

## Features

- **Flexible Input**: Download a single video via URL or process multiple videos using a text file.
- **Format Options**: Export to high-quality MP3 or MP4.
- **Quality Control**: Choose between 480p, 720p, and 1080p for video downloads.
- **Dry Run Mode**: Preview the download queue without consuming bandwidth.
- **Custom Storage**: Specify any local directory for your output files.
- **Detailed Logging**: Optional verbose mode for easier debugging.

## Prerequisites

- **Java 11** or newer (JDK 11+)
- **Maven 3.6+** (for building from source)

## Getting Started

### Installation & Build

1. Clone the repository:
   ```bash
   git clone https://github.com/shubhyagami/ytstream.git
   cd ytstream
   ```

2. Build the project:
   ```bash
   mvn clean package
   ```
   The executable JAR will be created in the `target/` directory (e.g., `target/ytstream-1.0.1.jar`).

### Basic Usage

Run the JAR using Java:
```bash
java -jar target/ytstream-*.jar [options]
```

To see all available commands, run:
```bash
java -jar target/ytstream-*.jar --help
```

## Command-Line Options

| Option | Required | Type | Description |
| :--- | :--- | :--- | :--- |
| `--url <URL>` | Yes* | String | A single YouTube video URL. |
| `--url-list <FILE>` | No | Path | Text file containing one URL per line. |
| `--output-format <mp3$\vert$mp4>` | No | Enum | Target format. Defaults to `mp4`. |
| `--quality <480p$\vert$720p$\vert$1080p>` | No | Enum | MP4 resolution. Ignored for MP3. |
| `--output-dir <DIR>` | No | Path | Destination directory. Defaults to current directory. |
| `--dry-run` | No | Flag | Log actions without downloading files. |
| `--verbose` | No | Flag | Enable detailed debug logging. |
| `--help` | No | Flag | Display help information. |

*\*Required unless `--url-list` is provided.*

## Examples

**Download a single video as MP3 to a specific folder:**
```bash
java -jar target/ytstream-*.jar \
  --url https://youtu.be/dQw4w9WgXcQ \
  --output-format mp3 \
  --output-dir ~/Music
```

**Batch download a list of videos at 720p:**
```bash
java -jar target/ytstream-*.jar \
  --url-list playlist.txt \
  --output-format mp4 \
  --quality 720p \
  --output-dir ~/Downloads/yt-videos
```

**Preview a batch download (Dry Run):**
```bash
java -jar target/ytstream-*.jar --url-list urls.txt --dry-run
```

## Contributing

Contributions are welcome! Please review the [CONTRIBUTING.md](CONTRIBUTING.md) and [CODE_OF_CONDUCT.md](CODE_OF_CONDUCT.md) guidelines before submitting a pull request.

## Changelog

**v1.0.1 (2026-08-05)**
- Introduced `--dry-run` flag for operation previews.
- Improved error handling and rotation for expired YouTube URLs.

Full version history can be found in [CHANGELOG.md](CHANGELOG.md).

## License

Distributed under the MIT License. See [LICENSE](LICENSE) for more information.
