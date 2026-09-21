# ytstream

![CI](https://img.shields.io/github/actions/workflow/status/shubhyagami/ytstream/ci.yml?branch=main&style=flat&logo=github)  
![License](https://img.shields.io/github/license/shubhyagami/ytstream?style=flat)  
![Java](https://img.shields.io/badge/java-11%2B-orange.svg)  
![PRs welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)

A lightweight, Java‑based CLI tool for downloading YouTube videos and converting them to **MP3** (audio) or **MP4** (video). It supports single URLs, playlist files, and batch processing with customizable quality and output settings.

## Features

- Flexible input: single URL or text file with multiple URLs  
- Export to MP3 or MP4  
- Video quality options: 480p, 720p, 1080p (ignored for MP3)  
- Dry‑run mode to preview the download queue  
- Custom output directory  
- Verbose logging for troubleshooting  

## Prerequisites

- Java 11 + (JDK 11 or newer)  
- Maven 3.6 + (to build from source)  

## Quick Start

```bash
git clone https://github.com/shubhyagami/ytstream.git
cd ytstream
mvn clean package
java -jar target/ytstream-*.jar --help
```

## Getting Started

### Build

```bash
mvn clean package
```

The executable JAR will appear in `target/` (e.g., `target/ytstream-1.0.1.jar`).

### Run

```bash
java -jar target/ytstream-*.jar [options]
```

Use `--help` to list all available command‑line options.

## Command‑Line Options

| Option | Required | Type | Description |
|--------|----------|------|-------------|
| `--url <URL>` | Yes* | String | A single YouTube video URL. |
| `--url-list <FILE>` | No | Path | Text file containing one URL per line. |
| `--output-format <mp3|mp4>` | No | Enum | Target format. Defaults to `mp4`. |
| `--quality <480p|720p|1080p>` | No | Enum | MP4 resolution. Ignored for MP3. |
| `--output-dir <DIR>` | No | Path | Destination directory. Defaults to the current working directory. |
| `--dry-run` | No | Flag | Log planned actions without downloading. |
| `--verbose` | No | Flag | Enable detailed debug logging. |
| `--help` | No | Flag | Display usage information. |

*Exactly one of `--url` or `--url-list` must be supplied.

## Examples

Download a single video as MP3 to a custom folder  

```bash
java -jar target/ytstream-*.jar \
    --url https://youtu.be/dQw4w9WgXcQ \
    --output-format mp3 \
    --output-dir ~/Music
```

Batch download a list of videos at 720p  

```bash
java -jar target/ytstream-*.jar \
    --url-list playlist.txt \
    --output-format mp4 \
    --quality 720p \
    --output-dir ~/Downloads/yt-videos
```

Preview a batch download (dry run)  

```bash
java -jar target/ytstream-*.jar --url-list urls.txt --dry-run
```

## Contributing

Pull requests are welcome. Please see the [CONTRIBUTING.md](CONTRIBUTING.md) and [CODE_OF_CONDUCT.md](CODE_OF_CONDUCT.md) documents before opening an issue or submitting a PR.

## Changelog

**1.0.1 – 2026‑08‑05**  
- Added `--dry-run` flag for previewing download queues.  
- Improved error handling and rotation for expired YouTube URLs.

Full changelog: [CHANGELOG.md](CHANGELOG.md)

## License

Distributed under the MIT License. See [LICENSE](LICENSE) for details.
