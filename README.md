# ytstream

A lightweight, Java‑based CLI tool that downloads a YouTube video and converts it to **MP3** (audio‑only) or **MP4** (video‑audio).  
It can handle a single URL, a playlist, or a batch file of URLs, and offers useful options such as resolution selection, dry‑run, and a custom output directory.

---

## Quick Start

1. Clone the repository  
   `git clone https://github.com/shubhyagami/ytstream.git`

2. Build the executable JAR  
   `mvn clean package`

3. Run the help command to see available options  
   `java -jar target/ytstream-*.jar --help`

---

## Prerequisites

- Java 11 or newer (JDK 11+)
- Maven 3.6+ for building

---

## Build

```bash
mvn clean package
```

The runnable JAR is placed in the `target/` directory, e.g. `target/ytstream-1.0.0.jar`.

---

## Running

```bash
java -jar target/ytstream-*.jar [options]
```

---

## Command‑Line Options

| Option | Required | Type | Description |
|--------|----------|------|-------------|
| `--url <URL>` | Yes (unless `--url-list` is used) | String | A single YouTube video URL. |
| `--url-list <FILE>` | No | Path | Text file with one YouTube URL per line. |
| `--output-format <mp3|mp4>` | No | Enum | Target format (`mp3` or `mp4`). Defaults to `mp4`. |
| `--quality <480p|720p|1080p>` | No | Enum | Desired MP4 resolution. Ignored for MP3. |
| `--output-dir <DIR>` | No | Path | Destination directory for downloads. Default is the current working directory. |
| `--dry-run` | No | Flag | Show actions without downloading. |
| `--verbose` | No | Flag | Enable detailed logging. |
| `--help` | No | Flag | Display help information. |

---

## Features

- **Batch downloads** – Process multiple URLs from a file.
- **Audio‑only export** – `--output-format mp3`.
- **Video‑audio export** – `--output-format mp4` with optional quality control.
- **Dry‑run** – Preview actions without network traffic.
- **Custom output directory** – Keep files organized.
- **Verbose logging** – Useful for troubleshooting.

---

## Examples

Convert a single video to MP3 and save it to your Music folder:

```bash
java -jar target/ytstream-*.jar \
  --url https://youtu.be/dQw4w9WgXcQ \
  --output-format mp3 \
  --output-dir ~/Music
```

Download a playlist (list of URLs in `playlist.txt`) at 720p MP4:

```bash
java -jar target/ytstream-*.jar \
  --url-list playlist.txt \
  --output-format mp4 \
  --quality 720p \
  --output-dir ~/Downloads/playlist
```

Dry‑run a batch download to see what would happen:

```bash
java -jar target/ytstream-*.jar \
  --url-list urls.txt \
  --dry-run
```

---

## Contributing

Pull requests are welcome!  
Please read the `[CONTRIBUTING.md]`(CONTRIBUTING.md) and `[CODE_OF_CONDUCT.md]`(CODE_OF_CONDUCT.md) before opening issues or PRs.

---

## Changelog

**v1.0.1 – 2026‑08‑05**

- Added `--dry-run` for previewing operations.  
- Improved handling of expired YouTube URLs.

Full history is available in the `[CHANGELOG.md]`(CHANGELOG.md).

---

## License

MIT – see the `[LICENSE]`(LICENSE) file.

---

## Badges

[![CI](https://img.shields.io/github/actions/workflow/status/shubhyagami/ytstream/ci.yml?branch=main&style=flat&logo=github)](https://github.com/shubhyagami/ytstream/actions)  
[![License](https://img.shields.io/github/license/shubhyagami/ytstream?style=flat)](LICENSE)  
[![Java](https://img.shields.io/badge/java-11%2B-orange.svg)](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)  
[![Contributions welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)](CONTRIBUTING.md)
