# ytstream

A command-line tool to extract and stream audio and video from YouTube. It supports single URLs, batch processing, and customizable output formats.

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen)](https://github.com/shubhyagami/ytstream/actions)
[![License](https://img.shields.io/badge/license-MIT-blue)](https://opensource.org/licenses/MIT)
[![Java Version](https://img.shields.io/badge/java-11%2B-orange)](https://www.java.com)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen)](CONTRIBUTING.md)

## Features

- Extract audio (`mp3`) or combined audio/video (`mp4`).
- Override automatic quality detection with standard resolutions (`480p`, `720p`, `1080p`).
- Batch processing via text files containing lists of URLs.
- Dry-run mode to preview download configurations before fetching data.
- Customizable output directories to keep downloads organized.
- Verbose logging for debugging network or parsing issues.

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

## Usage

Stream a YouTube video by URL:
```bash
java -jar target/ytstream-1.0.0.jar --url "https://youtu.be/dQw4w9WgXcQ"
```

### Command Reference

- **`--url`**: The URL of the YouTube video to process.
- **`--output-format`**: Extract audio only (`mp3`) or combined audio/video (`mp4`). Defaults to `mp4`.
- **`--quality`**: Override automatic quality detection. Accepts `480p`, `720p`, or `1080p`.
- **`--url-list`**: Process multiple videos by providing a text file with one URL per line.
- **`--dry-run`**: Preview what would be downloaded without fetching anything. Useful for testing configurations.
- **`--output-dir`**: Specify a custom directory (e.g., `./my_clips`) to keep downloads organized.
- **`--verbose`**: Output detailed logs for every step of the extraction process.

## Contributing

Contributions are welcome! Before submitting a pull request, please review the following guidelines.

### Reporting Issues

If you spot a bug, please open an issue. Include:
- Your operating system and Java version.
- Steps to reproduce the issue.
- Expected vs. actual behavior.

### Branching Policy

- The `main` branch is protected; no direct commits are allowed.
- Create feature branches from `main` with a clear prefix (e.g., `feature/add-playlist-support`, `bugfix/fix-audio-stall`).
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
