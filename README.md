# ytstream

A command-line tool to extract and stream audio and video from YouTube.

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen)](https://github.com/shubhyagami/ytstream/actions)
[![License](https://img.shields.io/badge/license-MIT-blue)](https://opensource.org/licenses/MIT)
[![Java Version](https://img.shields.io/badge/java-11%2B-orange)](https://www.java.com)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen)](CONTRIBUTING.md)
[![GitHub stars](https://img.shields.io/github/stars/shubhyagami/ytstream?style=social)](https://github.com/shubhyagami/ytstream/stargazers)

## Features

- Extract audio (`mp3`) or combined audio/video (`mp4`).
- Override automatic quality detection (supports `480p`, `720p`, `1080p`).
- Batch processing via text files containing URLs.
- Dry-run mode for testing downloads before fetching data.
- Customizable output directories.

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/shubhyagami/ytstream.git
   cd ytstream
   ```

2. **Build the project**
   ```bash
   mvn clean package
   ```

3. **Run the tool**
   ```bash
   java -jar target/ytstream-1.0.0.jar --help
   ```

### Usage

To stream a YouTube video by URL:
```bash
java -jar target/ytstream-1.0.0.jar --url "https://youtu.be/dQw4w9WgXcQ"
```

## Command Reference

- **Choose your output format**: Use `--output-format mp3` to extract audio only, or `--output-format mp4` for combined audio/video. Default is `mp4`.
- **Select quality**: Append `--quality 720p` (or `1080p`, `480p`) to override automatic quality detection.
- **Batch download**: Provide a text file with one URL per line using `--url-list playlist.txt` to process multiple videos in one command.
- **Dry-run mode**: Add `--dry-run` to see what would be downloaded without actually fetching anything—perfect for testing your configuration before committing.
- **Output directory**: Specify `--output-dir ./my_clips` to keep your downloads organized.
- **Verbose logging**: Use `--verbose` to watch every step of the extraction process—useful for debugging network or parsing issues.

## Contributing

Contributions are welcome! Before submitting a pull request, please review the following guidelines.

### Reporting Issues

If you spot a bug, please open an issue. Include:
- Your operating system and Java version.
- Steps to reproduce the issue.
- Expected vs. actual behavior.

### Branching Policy

- The `main` branch is protected; no direct commits are allowed.
- Create feature branches from `main` and name them with a clear prefix (e.g., `feature/add-playlist-support`, `bugfix/fix-audio-stall`).
- Ensure all tests pass and code follows the project's style guidelines before submitting a PR.

## Changelog

### v1.0.1 - 2026-08-05
- Added `--dry-run` flag for safe preview of download operations.
- Improved error handling for expired YouTube URLs.
- Introduced `--output-format` and `--quality` flags for finer control.
- Updated internal HTTP client to reduce latency during streams.
- Fixed a bug where audio extraction would stall on long videos.

### v1.0.0 - Initial Release
- Command-line tool for extracting and streaming YouTube audio and video.
- Support for single URL inputs, batch text files, and customizable output directories.
