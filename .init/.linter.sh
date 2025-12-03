#!/bin/bash
cd /home/kavia/workspace/code-generation/android-calendar-manager-217763-217774/android_frontend
./gradlew lint
LINT_EXIT_CODE=$?
if [ $LINT_EXIT_CODE -ne 0 ]; then
   exit 1
fi

