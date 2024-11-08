plugins {
    id("com.android.library").version(Versions.gradlePlugin).apply(false)
    kotlin("multiplatform").version(Versions.kotlin).apply(false)
    kotlin("plugin.serialization") version Versions.kotlin
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = Versions.kotlinJvmTarget
    }
}
