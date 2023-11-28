plugins {
    id("stopwatch.lint")
    id("stopwatch.android.feature")
}

android {
    namespace = "com.timers.stopwatch.feature.pomodoro"
}
dependencies {
    implementation(project(mapOf("path" to ":core:database")))
    implementation(project(mapOf("path" to ":core:data")))
}
