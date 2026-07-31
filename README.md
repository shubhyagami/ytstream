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

## Contributing (TVA Temporal Guidelines)

Greetings, Variant! You have been identified as a potential asset to the Sacred Timeline of ytstream. The Time Variance Authority welcomes all Nexus Events (contributions) that align with the tool’s mission to keep audio/video extraction stable and predictable. Before you prune or reset anything, please follow these temporal procedures:

### 1. Submit a Nexus Event (Issue)
Found a bug or have a feature request? File a **Temporal Anomaly Report** (GitHub Issue). Use the templates provided, and include the exact timeline coordinates (steps to reproduce). The TVA analysts will review it and assign a priority level: Low (minor deviation), Medium (potential branch), or Critical (incursion).

### 2. Create a Temporal Branch (Fork & Branch)
- **Fork** the repository to your own timeline.
- Create a **branch** with a name that follows the TVA naming convention:  
  `tva-<issue-number>-<short-description>`  
  Example: `tva-42-fix-audio-lag`

### 3. Make Your Changes in a Sacred Timeline
- Keep your changes **linear** – avoid unnecessary commits (we don't need branches splitting into chaos).
- Write **clear commit messages** as if you’re reporting to Mobius M. Mobius:  
  `[TVA-42] Fix audio desync when streaming at 2x speed`
- Ensure all tests pass (`mvn test`) – the TVA does not tolerate broken timelines.
- Update the README if your change affects usage or behaviour (the Temporal Guide must remain accurate).

### 4. Submit a Temporal Pull Request
- Push your branch to your fork and open a PR against the `sacred` (main) branch.
- In the PR description, explain **why** this change is necessary for the Sacred Timeline. Avoid creating “Nexus Events” (unnecessary changes).
- A TVA Analyst (maintainer) will review your PR. They may request adjustments – this is standard **time‑trimming**.
- Once approved, your branch will be **pruned** (merged) into the Sacred Timeline.

### 5. Sign the Temporal Oath (Code of Conduct)
By contributing, you agree to the [TVA Code of Conduct](CODE_OF_CONDUCT.md):  
- Respect all variants, regardless of timeline of origin.  
- No vandalism of the Sacred Timeline (no breaking builds).  
- If you create a branch that introduces a severe bug, you will be pruned (reverted) with dignity.

**Remember:** The TVA’s motto is *“For all time. Always.”* Your contributions help ytstream remain stable across all possible timelines. Thank you for your service, Agent.

*— The ytstream Temporal Engineering Team*