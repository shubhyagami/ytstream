[K[2m  [2mmodel z-ai/glm-5.3-flash failed, trying next...[0m[0m
# ytstream

[![CI](https://img.shields.io/github/actions/workflow/status/shubhyagami/ytstream/ci.yml?branch=main&style=flat&logo=github)](https://github.com/shubhyagami/ytstream/actions)
[![License](https://img.shields.io/github/license/shubhyagami/ytstream?style=flat)](LICENSE)
[![Java](https://img.shields.io/badge/java-11%2B-orange.svg)](https://www.oracle.com/java/)
[![Latest Release](https://img.shields.io/github/v/release/shubhyagami/ytstream.svg?style=flat&logo=github)](https://github.com/shubhyagami/ytstream/releases)
[![PRs welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)](CONTRIBUTING.md)

**ytstream** is a lightweight Java command-line tool for downloading YouTube videos. It accepts a single video URL or a text file containing multiple URLs, and can save the results as MP3 audio or MP4 video. Optional flags control output format, video quality, output directory, dry-run previews, and verbose logging.

> **Note:** This tool is intended for personal use. Please respect YouTube's Terms of Service and applicable copyright laws when downloading content.

## Features

- Download a single video or batch-download from a URL list file.
- Save output as MP3 audio or MP4 video.
- Choose MP4 quality: `480p`, `720p`, or `1080p`.
- Preview the download plan with `--dry-run` before downloading.
- Write results to any directory you choose.
- Enable verbose logging for troubleshooting.

## Getting Started

### Requirements

- Java 11 or later
- Maven 3.6+ (only required when building from source)

### Installation

**Pre-built JAR**

Download the latest release JAR from the [Releases](https://github.com/shubhyagami/ytstream/releases) page.

**From source**

    git clone https://github.com/shubhyagami/ytstream.git
    cd ytstream
    mvn clean package

The build produces an executable JAR in `target/` (for example, `target/ytstream-1.0.1.jar`). Verify the installation with:

    java -jar target/ytstream-*.jar --help

## Usage

    java -jar target/ytstream-*.jar [options]

### Command-line options

| Option | Required | Value | Description |
|--------|----------|-------|-------------|
| `--url <URL>` | One of¹ | String | Single YouTube video URL. |
| `--url-list <FILE>` | One of¹ | Path | Text file with one URL per line. |
| `--output-format <format>` | No | `mp3` or `mp4` | Target format. Defaults to `mp4`. |
| `--quality <quality>` | No | `480p`, `720p`, or `1080p` | Desired video resolution. Ignored for `mp3`. |
| `--output-dir <DIR>` | No | Path | Destination directory. Defaults to the current directory. |
| `--dry-run` | No | Flag | Show planned actions without downloading. |
| `--verbose` | No | Flag | Enable detailed debug logging. |
| `--help` | No | Flag | Display help. |

¹ Exactly one of `--url` or `--url-list` must be provided.

### Examples

**Download a single video as MP3**

    java -jar target/ytstream-*.jar \
      --url https://youtu.be/dQw4w9WgXcQ \
      --output-format mp3 \
      --output-dir ~/Music

**Batch download at 720p to a custom folder**

    java -jar target/ytstream-*.jar \
      --url-list playlist.txt \
      --output-format mp4 \
      --quality 720p \
      --output-dir ~/Downloads/yt-videos

**Preview a batch download**

    java -jar target/ytstream-*.jar \
      --url-list urls.txt \
      --dry-run

## Contributing

Pull requests are welcome. Please read the [contributing guidelines](CONTRIBUTING.md) and [code of conduct](CODE_OF_CONDUCT.md) before opening an issue or pull request. Make sure the test suite passes and the existing code style is followed.

    mvn test

## Changelog

### 1.0.1 – 2026
