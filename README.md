# ytstream

A lightweight command‑line utility for downloading and converting YouTube videos to **MP3** (audio‑only) or **MP4** (audio‑video).

![CI](https://img.shields.io/github/actions/workflow/status/shubhyagami/ytstream/ci.yml?branch=main&style=flat)
![License](https://img.shields.io/github/license/shubhyagami/ytstream.svg?style=flat)
![Java](https://img.shields.io/badge/java-11%2B-orange.svg)
![Contributions welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)

---

## Features

- Convert a single YouTube video to MP3 or MP4.
- Specify a resolution (`480p`, `720p`, or `1080p`) for video downloads.
- Download multiple videos from a text file containing one URL per line.
- Preview actions with the `--dry-run` flag (no network traffic).
- Choose a custom output directory.
- Verbose logging for detailed debugging.

---

## Prerequisites

- **Java 11+** (OpenJDK, Temurin, etc.)
- **Maven 3.6+**

---

## Building the project

```bash
git clone https://github.com/shubhyagami/ytstream.git
cd ytstream
mvn clean package
```

The executable JAR is located in `target/` (e.g. `ytstream-1.0.0.jar`).

---

## Usage

Run the JAR with the desired options.  
For a full list, use `--help`.

| Option               | Description |
|----------------------|-------------|
| `--url <URL>` | Single YouTube video URL (required unless `--url-list` is used). |
| `--url-list <FILE>` | Text file containing one URL per line. |
| `--output-format <mp3|mp4>` | `mp3` for audio‑only, `mp4` for video (default). |
| `--quality <480p|720p|1080p>` | Force a specific resolution for MP4 downloads. |
| `--output-dir <PATH>` | Directory where downloaded files will be stored. |
| `--dry-run` | Print the commands that would be executed without downloading. |
| `--verbose` | Emit detailed logs for each step. |
| `--help` | Show usage information. |

---

## Quick examples

```bash
# Download a single video as MP3
java -jar target/ytstream-1.0.0.jar --url https://youtu.be/xyz123 --output-format mp3

# Batch download from a file and store in ./downloads
java -jar target/ytstream-1.0.0.jar --url-list urls.txt --output-dir ./downloads
```

To preview what the command would do, add `--dry-run`.

---

## Contributing

Contributions are welcome! Before submitting a pull request, please read:

- [CONTRIBUTING.md](CONTRIBUTING.md)
- [CODE_OF_CONDUCT.md](CODE_OF_CONDUCT.md)

---

## Changelog

**v1.0.1 – 2026‑08‑05**

- Added `--dry-run` flag for safer previews.
- Improved handling of expired YouTube URLs.

See the full history in [CHANGELOG.md](CHANGELOG.md).

---

## License

MIT – see the [LICENSE](LICENSE) file.
