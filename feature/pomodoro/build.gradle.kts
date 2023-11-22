plugins {
    id("stopwatch.lint")
    id("stopwatch.android.feature")
    alias(libs.plugins.org.jetbrains.kotlin.android)
}

android {
    namespace = "com.timers.stopwatch.feature.pomodoro"
}
dependencies {
    implementation(project(mapOf("path" to ":core:database")))
    implementation(project(mapOf("path" to ":core:data")))
}
