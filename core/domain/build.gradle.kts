plugins {
    id("stopwatch.lint")
    id("stopwatch.android.library")
}

android {
    namespace = "com.timers.stopwatch.core.domain"
}

dependencies {
    implementation(projects.core.model)
    implementation(libs.kotlinx.coroutines.core.jvm)
    implementation(libs.inject)
}
