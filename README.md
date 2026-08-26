# ytstream
[![Build Status](https://img.shields.io/badge/build-passing-brightgreen)](https://github.com/shubhyagami/ytstream/actions)
[![License](https://img.shields.io/badge/license-MIT-blue)](https://opensource.org/licenses/MIT)
[![Java Version](https://img.shields.io/badge/java-11%2B-orange)](https://www.java.com)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen)](CONTRIBUTING.md)
[![Javadocs](https://img.shields.io/badge/javadocs-available-blue)](https://shubhyagami.github.io/ytstream/javadoc/)

ytstream is a command-line tool that extracts and streams audio and video from YouTube. It offers various features to make the process efficient and customizable.

## Key Features

*    **Audio and Video Extraction**: Obtain audio-only (mp3) or combined audio-video (mp4) from YouTube videos.
*    **Quality Control**: Override automatic quality detection with standard resolutions (480p, 720p, 1080p).
*    **Batch Processing**: Process multiple videos by providing a text file with one URL per line.
*    **Dry-Run Mode**: Preview download configurations without fetching data.
*    **Custom Output Directories**: Organize downloads using custom paths.
*    **Verbose Logging**: Output detailed logs for debugging network or parsing issues.

## Getting Started

Before using ytstream, ensure you have Java 11 or higher installed. You can then proceed to download and build the project.

```bash
git clone https://github.com/shubhyagami/ytstream.git
cd ytstream
mvn clean package
java -jar target/ytstream-1.0.0.jar --help
```

## Usage

To stream a YouTube video by URL:

```bash
java -jar target/ytstream-1.0.0.jar --url https://youtu.be/dQw4w9WgXcQ
```

### Command Reference

The following flags are available for customizing the extraction process:

*    **`--url`**: Required. The URL of the YouTube video to process.
*    **`--url-list`**: Process multiple videos from a text file with one URL per line.
*    **`--output-format`**: Optional. Extract audio-only (mp3) or combined audio-video (mp4). Defaults to mp4.
*    **`--quality`**: Optional. Override automatic quality detection. Accepts 480p, 720p, or 1080p.
*    **`--output-dir`**: Optional. Specify a custom directory (e.g., ./my\_clips) to keep downloads organized.
*    **`--dry-run`**: Optional. Preview what would be downloaded without fetching anything.
*    **`--verbose`**: Optional. Output detailed logs for every step of the extraction process.

## Contributing

Contributions are welcome! Before submitting a pull request, please review the [contributing guidelines](CONTRIBUTING.md).

## Changelog

### v1.0.1 - 2026-08-05
- Introduced `--dry-run` flag for safe preview of download operations.
- Improved error handling for expired YouTube URLs.
