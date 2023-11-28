plugins {
    id("stopwatch.lint")
    id("stopwatch.android.library")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.timers.stopwatch.core.data.source.api"
}

dependencies {
    implementation(projects.core.model)
    implementation(libs.kotlinx.coroutines.core.jvm)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
