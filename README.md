# NJR Benchmark — Java 11 Edition

This repository provides a Java 11–compatible edition of the NJR benchmark suite. The original NJR suite targeted Java 8 and is widely used for evaluating program-analysis and tooling workflows. Many modern tools and environments have moved beyond Java 8, so this edition updates the benchmarks to compile and run on Java 11 while preserving the original structure and intent.

> Please cite the original NJR release by Jens et al. ([DOI: 10.5281/zenodo.8015477](https://doi.org/10.5281/zenodo.8015477)). See **Credits & Citation** below.

## Why Java 11?

* Many analysis and build tools no longer support Java 8 out of the box.
* Components that were part of the JDK in Java 8 (e.g., JAXB, some XML/activation APIs) were removed from the JDK in Java 11 and now require explicit dependencies.
* This edition enables reproducible builds and evaluations on a current LTS JDK without changing the benchmark’s core behavior.

## What changed (high level)

* **Compilation target:** Updated to JDK 11.
* **APIs & imports:** Minor code adjustments in a subset of benchmarks to accommodate Java 11 API changes.
* **Dependencies:** Added external libraries to replace Java 8–bundled modules removed in Java 11 (e.g., JAXB-related artifacts), declared in the repo so benchmarks compile cleanly on JDK 11.
* **Scripts:** `scripts/compile_benchmarks.py` demonstrates the compilation flow on Java 11 and can serve as a reference for adapting tool runner scripts.

The goal is functional equivalence relative to the original Java 8 suite, with only the minimal changes necessary to build and execute under Java 11.

## Tested toolchain

This Java 11 edition has been validated with the following versions:

* **Checker Framework:** 3.49.0
* **Error Prone:** 2.28.0
* **JavaParser:** 3.25.4

Other tools from the original NJR ecosystem are expected to work on Java 11 with appropriate configuration, but they have not been exhaustively retested here. For guidance on updating tool runner scripts to Java 11, see `scripts/compile_benchmarks.py`.

## Requirements

* **JDK:** Java 11 (LTS)
* **Build/Run:** Standard Java toolchain (`javac`, `java`) and any tool-specific prerequisites as noted in the original NJR documentation.

## Quick start

```bash
# Ensure JDK 11 is active
java -version
# Compile benchmarks via the provided helper (adjust paths as needed)
python3 scripts/compile_benchmarks.py
# Example: run a benchmark jar if you know its main class
java -cp path/to/benchmark.jar com.example.Main
```

## Repository layout (abridged)

```
.
├─ benchmarks/                 # Benchmark projects and jars
├─ scripts/
│  ├─ compile_benchmarks.py    # Reference: how this suite is compiled on JDK 11
│  └─ run_<TOOL>.py            # Scripts for running tools on the benchmark suite
```

## Original NJR documentation

For background on the benchmarks, supported tools, and the original runner scripts, refer to the Java 8 README:

* [**README\_NJR\_J8.md**](README_NJR_J8.md) (in this repository)

That document remains the authoritative reference for tool descriptions and usage; this Java 11 edition focuses on compatibility updates and a verified build path on JDK 11.

## Credits & citation

* **Original NJR-1 dataset (Java 8):** Akshay Utture, Christian Gram Kalhauge, Shuyang Liu, and Jens Palsberg. *NJR-1 Dataset*, Version 1.0.1. Zenodo, 2020. [DOI: 10.5281/zenodo.8015477](https://doi.org/10.5281/zenodo.8015477).
* **Foundational paper:** Jens Palsberg and Cristina V. Lopes. *NJR: a Normalized Java Resource.* ISSTA 2018. [DOI: 10.1145/3236454.3236501](https://doi.org/10.1145/3236454.3236501).
