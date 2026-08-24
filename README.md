# ytstream
[![Build Status](https://img.shields.io/badge/build-passing-brightgreen)](https://github.com/shubhyagami/ytstream/actions)
[![License](https://img.shields.io/badge/license-MIT-blue)](https://opensource.org/licenses/MIT)
[![Java Version](https://img.shields.io/badge/java-11%2B-orange)](https://www.java.com)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen)](CONTRIBUTING.md)

ytstream is a command-line tool for extracting and streaming audio and video from YouTube. It supports processing single URLs, batch operations via text files, and customizable output formats.

### Features

- **Audio and Video Extraction**: Extract audio only (`mp3`) or combined audio/video (`mp4`).
- **Quality Control**: Override automatic quality detection with standard resolutions (`480p`, `720p`, `1080p`).
- **Batch Processing**: Process multiple videos by providing a text file with one URL per line.
- **Dry-Run Mode**: Preview download configurations before fetching data.
- **Custom Output Directories**: Keep your downloads organized with custom paths.
- **Verbose Logging**: Output detailed logs for debugging network or parsing issues.

### Getting Started

Before using ytstream, ensure you have Java 11 or higher installed, along with Maven.

```bash
git clone https://github.com/shubhyagami/ytstream.git
cd ytstream
mvn clean package
java -jar target/ytstream-1.0.0.jar --help
```

### Usage

To stream a YouTube video by URL:

```bash
java -jar target/ytstream-1.0.0.jar --url "https://youtu.be/dQw4w9WgXcQ"
```

See the command reference below for available flags and their descriptions.

### Command Reference

- **`--url`**: The URL of the YouTube video to process.
- **`--url-list`**: Process multiple videos by providing a text file with one URL per line.
- **`--output-format`**: Extract audio only (`mp3`) or combined audio/video (`mp4`). Defaults to `mp4`.
- **`--quality`**: Override automatic quality detection. Accepts `480p`, `720p`, or `1080p`.
- **`--output-dir`**: Specify a custom directory (e.g., `./my_clips`) to keep downloads organized.
- **`--dry-run`**: Preview what would be downloaded without fetching anything. Useful for testing configurations.
- **`--verbose`**: Output detailed logs for every step of the extraction process.

### Contributing

Contributions are welcome! Before submitting a pull request, please review the contributing guidelines.

### Changelog

### v1.0.1 - 2026-08-05
- Added `--dry-run` flag for safe preview of download operations.
- Improved error handling for expired YouTube URLs.

### v1.0.0 - Initial Release
- Command-line tool for extracting and streaming YouTube audio and video.
- Support for single URL inputs, batch text files, and customizable output directories.
