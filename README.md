# ytstream

Command‑line utility to download YouTube videos and convert them to **MP3** (audio‑only) or **MP4** (video‑audio).

---

## 🚀 Quick Start

```bash
# Clone the repo
git clone https://github.com/shubhyagami/ytstream.git
cd ytstream

# Build the JAR
mvn clean package

# Show help
java -jar target/ytstream-*.jar --help

# Download a single video as MP3
java -jar target/ytstream-*.jar \
  --url https://youtu.be/xyz123 \
  --output-format mp3

# Batch download from a file, saving into ./downloads
java -jar target/ytstream-*.jar \
  --url-list urls.txt \
  --output-dir ./downloads
```

---

## 📦 Installation

The project is built with Maven. After cloning, run:

```bash
mvn clean package
```

The runnable JAR will be in `target/` (e.g. `ytstream-1.0.0.jar`).  
This JAR is self‑contained and requires only a Java 11+ runtime.

---

## 📚 Usage

`ytstream` accepts a single URL or a file containing a list of URLs.  
It can output either MP3 or MP4, optionally selecting a resolution for MP4.

```bash
java -jar target/ytstream-*.jar [options]
```

| Option | Required | Description |
|--------|----------|-------------|
| `--url <URL>` | **Yes** (unless `--url-list` is supplied) | A single YouTube video URL. |
| `--url-list <FILE>` | No | Path to a text file with one YouTube URL per line. |
| `--output-format <mp3|mp4>` | No | Target format (`mp3` for audio, `mp4` for video‑audio). Defaults to `mp4`. |
| `--quality <480p|720p|1080p>` | No | Force a specific MP4 resolution for video downloads. |
| `--output-dir <PATH>` | No | Directory where downloaded files will be stored. Defaults to the current working directory. |
| `--dry-run` | No | Show the actions that would be performed without downloading. |
| `--verbose` | No | Emit detailed logs for each step. |
| `--help` | No | Display this help message. |

---

## 🎥 Features

- **Batch downloads** – supply a file of URLs to download many videos at once.  
- **Format conversion** – `--output-format mp3` (audio‑only) or `--output-format mp4` (video‑audio).  
- **Resolution control** – `--quality 480p|720p|1080p` for MP4 downloads.  
- **Dry‑run** – preview actions without performing downloads.  
- **Custom output directory** – `--output-dir <path>`.  
- **Verbose logging** – `--verbose` prints detailed progress information.  
- **Help** – `--help` displays usage information.

---

## 📄 Examples

```bash
# Convert a single video to MP3 and save to ~/Music
java -jar target/ytstream-*.jar \
  --url https://youtu.be/dQw4w9WgXcQ \
  --output-format mp3 \
  --output-dir ~/Music

# Download a playlist (one URL per line) at 720p
java -jar target/ytstream-*.jar \
  --url-list playlist.txt \
  --output-format mp4 \
  --quality 720p \
  --output-dir ~/Downloads/playlist
```

---

## 🤝 Contributing

Pull requests are always welcome!  
Please refer to the [CONTRIBUTING.md](CONTRIBUTING.md) and [CODE_OF_CONDUCT.md](CODE_OF_CONDUCT.md) documents for details.

---

## 📜 Changelog

**v1.0.1 – 2026‑08‑05**

- Added `--dry-run` flag for previewing operations.  
- Improved handling of expired YouTube URLs.

Full release history is in the [CHANGELOG.md](CHANGELOG.md).

---

## 📜 License

MIT – see the [LICENSE](LICENSE) file.

---

## 📗 Badges

![CI](https://img.shields.io/github/actions/workflow/status/shubhyagami/ytstream/ci.yml?branch=main&style=flat&logo=github)
![License](https://img.shields.io/github/license/shubhyagami/ytstream?style=flat)
![Java](https://img.shields.io/badge/java-11%2B-orange.svg)
![Contributions welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)
