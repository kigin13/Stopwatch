plugins {
    id("stopwatch.lint")
    id("stopwatch.android.library")
}
android {
    namespace = "com.timers.stopwatch.core.log"
}

dependencies {
    implementation(libs.timber)
}
