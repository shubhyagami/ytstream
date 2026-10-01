[K[2m  [2mmodel z-ai/glm-5.3-flash failed, trying next...[0m[0m
[K[2m  [2mmodel deepseek-ai/deepseek-v4.1-flash failed, trying next...[0m[0m
# ytstream

[![CI](https://img.shields.io/github/actions/workflow/status/shubhyagami/ytstream/ci.yml?branch=main&style=flat&logo=github)](https://github.com/shubhyagami/ytstream/actions)
[![License](https://img.shields.io/github/license/shubhyagami/ytstream?style=flat)](LICENSE)
[![Java](https://img.shields.io/badge/java-11%2B-orange.svg)](https://www.oracle.com/java/)
[![Latest Release](https://img.shields.io/github/v/release/shubhyagami/ytstream.svg?style=flat&logo=github)](https://github.com/shubhyagami/ytstream/releases)
[![PRs welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)](CONTRIBUTING.md)

**ytstream** is a lightweight Java command‑line tool that downloads YouTube videos or audio.  
It accepts a single video URL or a text file with multiple URLs, and can output either MP3 audio or MP4 video at selectable quality.

> **Disclaimer**  
> Use ytstream responsibly. Respect YouTube’s Terms of Service and all applicable copyright laws.

## Features

- Download a single video or batch‑download from a list of URLs.
- Export as MP3 (audio only) or MP4 (video).
- Choose video resolution: `480p`, `720p`, `1080p`.
- Preview the download plan with `--dry-run`.
- Specify an output directory.
- Enable verbose logging for troubleshooting.

## Getting Started

```bash
# Download a single video as MP3
java -jar ytstream-<version>.jar \
  --url https://youtu.be/dQw4w9WgXcQ \
  --output-format mp3 \
  --output-dir ~/Music
```

## Requirements

- Java 11 or newer
- Maven 3.6+ (only required for building from source)

## Installation

### Pre‑built JAR

Download the latest release JAR from the [Releases](https://github.com/shubhyagami/ytstream/releases) page.

```bash
java -jar ytstream-<version>.jar --help
```

### From source

```bash
git clone https://github.com/shubhyagami/ytstream.git
cd ytstream
mvn clean package
# Executable JAR is created at target/ytstream-<version>.jar
```

## Usage

```bash
java -jar target/ytstream-<version>.jar [options]
```

### Command‑line options

| Option | Required | Value | Description |
|--------|----------|-------|-------------|
| `--url <URL>` | One of¹ | String | Single YouTube video URL. |
| `--url-list <FILE>` | One of¹ | Path | Text file with one URL per line. |
| `--output-format <format>` | No | `mp3` or `mp4` | Desired output format; defaults to `mp4`. |
| `--quality <quality>` | No | `480p`, `720p`, `1080p` | Video resolution; ignored when outputting MP3. |
| `--output-dir <DIR>` | No | Path | Destination directory; defaults to the current directory. |
| `--dry-run` | No | Flag | Show planned actions without downloading. |
| `--verbose` | No | Flag | Enable detailed debug logging. |
| `--help` | No | Flag | Display help. |

¹ Exactly one of `--url` or `--url-list` must be provided.

### Examples

**1. Download a single video as MP3**

```bash
java -jar target/ytstream-<version>.jar \
  --url https://youtu.be/dQw4w9WgXcQ \
  --output-format mp3 \
  --output-dir ~/Music
```

**2. Batch download at 720p to a custom folder**

```bash
java -jar target/ytstream-<version>.jar \
  --url-list playlist.txt \
  --output-format mp4 \
  --quality 720p \
  --output-dir ~/Downloads/yt-videos
```

**3. Preview a batch download**

```bash
java -jar target/ytstream-<version>.jar \
  --url-list urls.txt \
  --dry-run
```

## Contributing

Pull requests are welcome!  
Please read the [contributing guidelines](CONTRIBUTING.md) and [code of conduct](CODE_OF_CONDUCT.md) before opening an issue or pull request.  
Run the tests to ensure the suite passes:

```bash
mvn test
```

## Changelog

### 1.0.1 – 2026

- Minor bug fixes and performance improvements.

#
