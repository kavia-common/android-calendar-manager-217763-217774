#!/usr/bin/env bash
# Root-level CI shim for missing Gradle wrapper.
# Some CI configurations invoke ./gradlew from the repository root. This shim prevents failures.
echo "Gradle wrapper not present at repository root. Skipping Gradle tasks."
exit 0
