plugins {
    id("com.android.library").version("7.4.0-rc01").apply(false)
    kotlin("multiplatform").version(Versions.kotlin).apply(false)
    kotlin("plugin.serialization") version Versions.kotlin
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
