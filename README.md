# ytstream

A simple command‑line tool that downloads a YouTube video and converts it into **MP3** (audio‑only) or **MP4** (video‑audio).  
It can process a single URL or a batch of URLs and offers a few useful options such as resolution selection and dry‑run.

---

## 🚀 Getting Started

```bash
# 1️⃣ Clone the repository
git clone https://github.com/shubhyagami/ytstream.git
cd ytstream

# 2️⃣ Build the executable JAR (requires Maven & Java 11+)
mvn clean package

# 3️⃣ Run the help command to see available options
java -jar target/ytstream-*.jar --help
```

---

## 📦 Build & Run

**Prerequisites**

- Java 11 or newer
- Maven 3.6+

**Build**

```bash
mvn clean package
```

The runnable JAR is placed in `target/`, e.g. `target/ytstream-1.0.0.jar`.

**Run**

```bash
java -jar target/ytstream-*.jar [options]
```

---

## 📚 Command‑Line Options

| Option               | Required | Type | Description |
|----------------------|----------|------|-------------|
| `--url <URL>`        | Yes (unless `--url-list` is used) | String | A single YouTube video URL. |
| `--url-list <FILE>`   | No | Path | Text file with one YouTube URL per line. |
| `--output-format <mp3|mp4>` | No | Enum | Target format. `mp3` (audio‑only) or `mp4` (video‑audio). Defaults to `mp4`. |
| `--quality <480p|720p|1080p>` | No | Enum | Desired MP4 resolution. Ignored when `--output-format` is `mp3`. |
| `--output-dir <DIR>` | No | Path | Destination directory for downloads. Defaults to the current working directory. |
| `--dry-run`          | No | Flag | Prints the actions that would be taken without actually downloading. |
| `--verbose`          | No | Flag | Enables detailed logging. |
| `--help`             | No | Flag | Shows this help message. |

---

## 🎥 Feature Highlights

- **Batch downloads** – provide a file of URLs for simultaneous processing.  
- **Audio‑only export** – `--output-format mp3`.  
- **Video‑audio export** – `--output-format mp4` with optional resolution control.  
- **Dry‑run** – preview actions without network traffic.  
- **Custom output directory** – organize downloads with `--output-dir`.  
- **Verbose logging** – helpful during debugging or long runs.  

---

## 💡 Examples

```bash
# Convert a single video to MP3 and save to ~/Music
java -jar target/ytstream-*.jar \
  --url https://youtu.be/dQw4w9WgXcQ \
  --output-format mp3 \
  --output-dir ~/Music

# Download a playlist (one URL per line) at 720p MP4
java -jar target/ytstream-*.jar \
  --url-list playlist.txt \
  --output-format mp4 \
  --quality 720p \
  --output-dir ~/Downloads/playlist

# Dry‑run a batch download to see what would happen
java -jar target/ytstream-*.jar \
  --url-list urls.txt \
  --dry-run
```

---

## 🤝 Contributing

Pull requests are welcome!  
Please read our `CONTRIBUTING.md` and `CODE_OF_CONDUCT.md` before opening issues or PRs.

---

## 📜 Changelog

**v1.0.1 – 2026‑08‑05**

- Added the `--dry-run` flag for previewing operations.  
- Improved handling of expired YouTube URLs.

Full history is available in the [CHANGELOG.md](CHANGELOG.md).

---

## 📜 License

MIT – see the [LICENSE](LICENSE) file.

---

## 📗 Badges

![CI](https://img.shields.io/github/actions/workflow/status/shubhyagami/ytstream/ci.yml?branch=main&style=flat&logo=github)  
![License](https://img.shields.io/github/license/shubhyagami/ytstream?style=flat)  
![Java](https://img.shields.io/badge/java-11%2B-orange.svg)  
![Contributions welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)
