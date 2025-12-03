# Android Frontend Build Notes

This repository does not currently include a complete Android project or the Gradle wrapper.
A lightweight `gradlew` shim is provided to prevent CI failures in multi-container pipelines.

When integrating the full Android app, replace the shim with a real Gradle wrapper:
1. On a machine with Gradle installed, run:
   gradle wrapper --gradle-version 8.7
2. Commit the generated files:
   - gradlew
   - gradlew.bat
   - gradle/wrapper/gradle-wrapper.jar
   - gradle/wrapper/gradle-wrapper.properties
3. Ensure executable permissions:
   chmod +x gradlew
