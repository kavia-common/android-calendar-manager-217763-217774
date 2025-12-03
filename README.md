# Repository Root Gradle Wrapper Shim

Some CI pipelines invoke `./gradlew` from the repository root. This repository does not include a full Android project yet,
so a lightweight shim is provided to avoid CI failures.

To replace with a real Gradle wrapper once the Android project is integrated:
1. On a machine with Gradle installed, run:
   gradle wrapper --gradle-version 8.7
2. Commit the generated files:
   - gradlew
   - gradlew.bat
   - gradle/wrapper/gradle-wrapper.jar
   - gradle/wrapper/gradle-wrapper.properties
3. Ensure executable permission:
   chmod +x gradlew
