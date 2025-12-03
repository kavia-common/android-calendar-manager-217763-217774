# android-calendar-manager-217763-217774

This repository hosts a multi-container project:
- database (PostgreSQL)
- backend (Spring Boot on port 3001)
- android_frontend (Android app UI)

Quick start:

Database
- Ensure a running PostgreSQL and .env with POSTGRES_URL, POSTGRES_USER, POSTGRES_PASSWORD.
- See android-calendar-manager-217763-217772/database/README.md
- Apply schema is automatic via JPA (ddl-auto=update). Alternatively, create table manually:

  CREATE TABLE IF NOT EXISTS events (
    id uuid PRIMARY KEY,
    title text NOT NULL,
    description text,
    start_time timestamptz NOT NULL,
    end_time timestamptz NOT NULL,
    location text,
    all_day boolean NOT NULL DEFAULT false,
    recurrence_rule text,
    created_at timestamptz NOT NULL DEFAULT now(),
    updated_at timestamptz NOT NULL DEFAULT now()
  );

Backend
- cd android-calendar-manager-217763-217773/backend
- Configure env vars POSTGRES_URL, POSTGRES_USER, POSTGRES_PASSWORD
- Run: ./gradlew bootRun
- APIs on http://localhost:3001/api/events

Android
- cd android-calendar-manager-217763-217774/android_frontend
- Build and run with Android Studio/Gradle.
- Emulator will reach backend via 10.0.2.2:3001

Notes
- No secrets are hard-coded.
- API supports CRUD and list by date range.
