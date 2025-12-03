plugins {
    // Define but do not apply at the root; modules will apply their own plugins
    id("com.android.application") apply false
    id("com.android.library") apply false
    kotlin("android") apply false
}
