# ytstream

A lightweight command‑line tool for extracting and streaming audio or video from YouTube.

[![CI](https://img.shields.io/github/actions/workflow/status/shubhyagami/ytstream/ci.yml?branch=main&style=flat)](https://github.com/shubhyagami/ytstream/actions)
[![License](https://img.shields.io/github/license/shubhyagami/ytstream.svg?style=flat)](LICENSE)
[![Java](https://img.shields.io/badge/java-11%2B-orange.svg)](https://adoptium.net/temurin11/)
[![Contributions welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)](https://github.com/shubhyagami/ytstream/pulls)

## Table of Contents
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
  - [Single video](#single-video)
  - [Batch download](#batch-download)
  - [Command‑line options](#command-line-options)
- [Configuration](#configuration)
- [Contributing](#contributing)
- [Changelog](#changelog)
- [License](#license)

## Features
- Extract audio only (MP3) or combined audio‑video (MP4).
- Override default quality (480 p, 720 p, or 1080 p).
- Process a list of URLs for bulk downloads.
- Dry‑run mode to preview downloads without network access.
- Customize the output directory.
- Verbose logging for debugging.

## Prerequisites
- Java 11 or newer (OpenJDK, Temurin, etc.).
- Maven 3.6+.

## Installation
```bash
git clone https://github.com/shubhyagami/ytstream.git
cd ytstream
mvn clean package
```
The JAR is created in `target/`.

## Usage
The tool is run via the generated JAR. All options are prefixed with `--`.

### Single video
```bash
java -jar target/ytstream-1.0.0.jar --url https://youtu.be/dQw4w9WgXcQ
```

### Batch download
Create a text file (e.g., `urls.txt`) containing one YouTube URL per line and run:
```bash
java -jar target/ytstream-1.0.0.jar --url-list urls.txt
```

### Command‑line options
| Option                     | Description |
|---------------------------|-------------|
| `--url <URL>`             | YouTube video URL (required for single‑video mode). |
| `--url-list <FILE>`       | Path to a file with multiple URLs. |
| `--output-format <mp3|mp4>` | Choose `mp3` (audio‑only) or `mp4` (audio‑video). Default: `mp4`. |
| `--quality <480p|720p|1080p>` | Force a specific resolution. |
| `--output-dir <PATH>`     | Directory where files will be saved. |
| `--dry-run`               | Print the download plan without fetching data. |
| `--verbose`               | Emit detailed logs for each step. |

#### Quick help
```bash
java -jar target/ytstream-1.0.0.jar --help
```

## Configuration
All settings are supplied on the command line; no external configuration file is required.

## Contributing
Feel free to open issues or submit pull requests.  
Please review the [CONTRIBUTING.md](CONTRIBUTING.md) and [CODE_OF_CONDUCT.md](CODE_OF_CONDUCT.md) before contributing.

## Changelog
See the [CHANGELOG.md](CHANGELOG.md) for the full history.

**v1.0.1 – 2026‑08‑05**
- Added `--dry-run` flag for safe preview of download operations.  
- Improved error handling for expired YouTube URLs.

## License
MIT – see the [LICENSE](LICENSE) file.
