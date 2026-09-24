[K[2m  [2mmodel deepseek-ai/deepseek-v4.1-flash failed, trying next...[0m[0m
# ytstream

[![CI](https://img.shields.io/github/actions/workflow/status/shubhyagami/ytstream/ci.yml?branch=main&style=flat&logo=github)](https://github.com/shubhyagami/ytstream/actions)
[![License](https://img.shields.io/github/license/shubhyagami/ytstream?style=flat)](LICENSE)
[![Java](https://img.shields.io/badge/java-11%2B-orange.svg)](https://www.oracle.com/java/)
[![Latest Release](https://img.shields.io/github/v/release/shubhyagami/ytstream.svg?style=flat&logo=github)](https://github.com/shubhyagami/ytstream/releases)
[![PRs welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)](CONTRIBUTING.md)

**ytstream** is a lightweight Java command‑line tool that downloads YouTube videos and can convert them to MP3 audio or MP4 video.  
It accepts a single YouTube URL or a file with multiple URLs and offers options for:

- output format (`mp3` or `mp4`)
- video quality (`480p`, `720p`, `1080p`; ignored for `mp3`)
- output directory
- dry‑run preview
- verbose logging

---

## 📦 Installation

**Pre‑built binaries**  
Download the latest release JAR from the [Releases](https://github.com/shubhyagami/ytstream/releases) page.

**From source**  
```bash
git clone https://github.com/shubhyagami/ytstream.git
cd ytstream
mvn clean package
```
The JAR will be available in `target/` (e.g. `target/ytstream-1.0.1.jar`).

You can confirm the installation with:
```bash
java -jar target/ytstream-*.jar --help
```

---

## 🚀 Usage

```bash
java -jar target/ytstream-*.jar [options]
```

### Command‑Line Options

| Option | Required | Value | Description |
|--------|----------|-------|-------------|
| `--url <URL>` | Yes, **or** `--url-list` | String | Single YouTube video URL. |
| `--url-list <FILE>` | Yes, **or** `--url` | Path | Text file with one URL per line. |
| `--output-format <mp3|mp4>` | No | Enum | Target format. Default: `mp4`. |
| `--quality <480p|720p|1080p>` | No | Enum | Desired video resolution (ignored for `mp3`). |
| `--output-dir <DIR>` | No | Path | Destination directory. Default: current working directory. |
| `--dry-run` | No | Flag | Show planned actions without downloading. |
| `--verbose` | No | Flag | Enable detailed debug logging. |
| `--help` | No | Flag | Display help. |

> **Note:** Provide exactly one of `--url` **or** `--url-list`.

### Examples

**Download a single video as MP3**

```bash
java -jar target/ytstream-*.jar \
  --url https://youtu.be/dQw4w9WgXcQ \
  --output-format mp3 \
  --output-dir ~/Music
```

**Batch download at 720p to a custom folder**

```bash
java -jar target/ytstream-*.jar \
  --url-list playlist.txt \
  --output-format mp4 \
  --quality 720p \
  --output-dir ~/Downloads/yt-videos
```

**Preview a batch download**

```bash
java -jar target/ytstream-*.jar \
  --url-list urls.txt \
  --dry-run
```

---

## ✨ Features

* Download a single video or batch‑download from a file.
* Convert to MP3 or MP4.
* Select video quality for MP4 (480p, 720p, 1080p).
* Preview download plan with `--dry-run`.
* Specify a custom output directory.
* Verbose mode for debugging.

---

## 🤝 Contributing

Pull requests are welcome!  
Please read the [Contributing](CONTRIBUTING.md) and [Code of Conduct](CODE_OF_CONDUCT.md) before opening an issue or PR. Ensure all tests pass and style guidelines are followed.

---

## 🗓️ Changelog

### 1.0.1 – 2026‑08‑05

* Added `--dry-run` for previewing download queues.
* Improved error handling for expired YouTube URLs.

See the full history in [CHANGELOG.md](CHANGELOG.md).

---

## 📄 License

Distributed under the MIT License. See [LICENSE](LICENSE) for details.
