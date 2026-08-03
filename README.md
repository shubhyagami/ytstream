Here is the complete updated README with the new TVA-themed "Contributing" section appended.

---

# ytstream

▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄
█  ╔═══════════════════════╗  █
█  ║  ▶ ytstream v1.0     ║  █
█  ╚═══════════════════════╝  █
▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen)](https://github.com/shubhyagami/ytstream/actions)
[![License](https://img.shields.io/badge/license-MIT-blue)](https://opensource.org/licenses/MIT)
[![Java Version](https://img.shields.io/badge/java-11%2B-orange)](https://www.java.com)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen)](CONTRIBUTING.md)
[![GitHub stars](https://img.shields.io/github/stars/shubhyagami/ytstream?style=social)](https://github.com/shubhyagami/ytstream/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/shubhyagami/ytstream?style=social)](https://github.com/shubhyagami/ytstream/network)
[![Downloads](https://img.shields.io/github/downloads/shubhyagami/ytstream/total)](https://github.com/shubhyagami/ytstream/releases)
[![Maintenance](https://img.shields.io/badge/maintained-yes-success)](https://github.com/shubhyagami/ytstream/graphs/commit-activity)
[![Temporal Compliance](https://img.shields.io/badge/temporal_alignment-100%25-purple)](https://github.com/shubhyagami/ytstream)

> *"Time is what we want most, but what we use worst. With ytstream, you take control of your temporal bandwidth—extracting audio and video on your terms, synchronized to your timeline."* — TVA Temporal Engineering 

## Quick Start

Get up and running with `ytstream` in three simple steps:

1. **Clone the repository**
   ```bash
   git clone https://github.com/shubhyagami/ytstream.git
   cd ytstream
   ```

2. **Build the project** (requires Java 11+ and Maven)
   ```bash
   mvn clean package
   ```

3. **Run the tool**
   ```bash
   java -jar target/ytstream-1.0.0.jar --help
   ```

   For example, to stream a YouTube video by URL:
   ```bash
   java -jar target/ytstream-1.0.0.jar --url "https://youtu.be/dQw4w9WgXcQ"
   ```

## Pro Tips

---

## Contributing to ytstream (TVA Guidelines)

Welcome, variant! Your timeline branch has intersected with ours. The TVA Temporal Engineering department values all contributions that maintain the **Sacred Timeline** of code quality. Before you submit a pull request, please review the following temporal protocols.

### 🔍 Detect Temporal Variances (Reporting Issues)

If you spot a bug (a *Nexus Event* in the code), open an issue. Include:
- Your operating system and Java version (the **temporal coordinates** of your environment).
- Steps to reproduce the anomaly.
- Expected vs. actual behavior (the *divergence point*).

We’ll dispatch a Minuteman (aka a maintainer) to investigate.

### 🌿 Branching Policy (Pruning Unnecessary Timelines)

- **Main branch** (`main`) is the **Sacred Timeline**. No direct commits.
- Create feature branches from `main` and name them with a temporal prefix:
  - `feat/<short-description>` – for new features
  - `fix/<short-description>` – for bug fixes
  - `docs/<short-description>` – for documentation updates
- Keep your branch rebased on the latest `main` to avoid *temporal paradoxes* (merge conflicts).

### 🛠 Pull Request Standards (TVA Accreditation)

Every PR must pass these checks before the **Time Variance Authority** approves it:

1. **Code compiles** – no broken timelines.
2. **Tests pass** – `mvn test` must report zero failures (all *variants* behave as expected).
3. **New code is tested** – aim for ≥80% coverage on new classes (we don’t want *unstable loop* regression).
4. **Formatting matches** – use the project’s style (4 spaces, no tabs). A mismatched brace is a *temporal infraction*.
5. **One logical change per PR** – don’t mix a bug fix with a feature request. We don’t approve *chaos magic*.

### 📖 Commit Messages (Temporal Logging)

Use conventional commits to keep the timeline legible:
```
feat: add --format option for output container selection
fix: handle null URL in YouTubeUrlValidator
docs: update README with new configuration flags
```

### 🧪 Testing (Variant Simulation)

We use JUnit 5 and Mockito. Run the full suite:
```bash
mvn test
```
If you introduce a new feature, add a corresponding test class under `src/test/java`. Every new method is a *new timeline branch* – we need to verify it doesn’t collapse the main timeline.

### 📜 License

By contributing, you agree that your contributions are licensed under the MIT License – the most *stable temporal artifact* in the open-source multiverse.

---

**Ready to join the TVA?** Fork the repo, create a branch, and open a PR. Our Temporal Engineers will review your submission and, if it passes all checks, *reset* the timeline to include your changes.

**Remember:** *All code, all the time. Prune the bugs, preserve the features.*

— TVA Temporal Engineering Division, 2026-08-04