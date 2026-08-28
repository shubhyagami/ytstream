# ytstream

A lightweight command‑line tool for extracting and streaming audio and video from YouTube. It supports single‑video downloads, batch processing, custom output directories, and detailed logging.

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen)](https://github.com/shubhyagami/ytstream/actions)
[![License](https://img.shields.io/badge/license-MIT-blue)](https://opensource.org/licenses/MIT)
[![Java Version](https://img.shields.io/badge/java-11%2B-orange)](https://www.oracle.com/java/technologies/downloads/)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen)](https://github.com/shubhyagami/ytstream/graphs/contributors)

## Features

- **Audio‑only (mp3) or combined audio‑video (mp4) extraction**  
- **Quality override** – force 480p, 720p, or 1080p resolutions  
- **Batch mode** – process a list of URLs from a text file  
- **Dry‑run preview** – see what would be downloaded without actually fetching data  
- **Custom output directories** – keep your files organized  
- **Verbose logging** – helpful debug output for network or parsing issues  

## Getting Started

1. **Install Java 11 or newer**  
2. Clone the repository and build the project  

```text
git clone https://github.com/shubhyagami/ytstream.git
cd ytstream
mvn clean package
```

3. Verify the installation with the help command  

```text
java -jar target/ytstream-1.0.0.jar --help
```

## Usage

### Single video

```text
java -jar target/ytstream-1.0.0.jar --url https://youtu.be/dQw4w9WgXcQ
```

### Multiple videos

Create a file (e.g., `urls.txt`) containing one YouTube URL per line and run:

```text
java -jar target/ytstream-1.0.0.jar --url-list urls.txt
```

### Command reference

| Flag                | Description                                                                 |
|---------------------|-----------------------------------------------------------------------------|
| `--url <URL>`       | YouTube video URL to process (required for single‑video mode)               |
| `--url-list <FILE>` | Path to a text file containing multiple URLs                                 |
| `--output-format <mp3|mp4>` | Choose audio‑only (`mp3`) or combined audio‑video (`mp4`). Defaults to `mp4`. |
| `--quality <480p|720p|1080p>` | Override automatic quality detection.                                      |
| `--output-dir <PATH>`   | Directory where downloads will be saved.                                     |
| `--dry-run`           | Show the planned download without actually fetching data.                    |
| `--verbose`           | Emit detailed logs for each step of the extraction process.                 |

## Contributing

Contributions are welcome. Please read the [contributing guidelines](CONTRIBUTING.md) before submitting a pull request.

## Changelog

### v1.0.1 – 2026‑08‑05
- Added `--dry-run` flag for safe preview of download operations.  
- Improved error handling for expired YouTube URLs.  

## License

This project is licensed under the MIT License – see the [License](LICENSE) file for details.
