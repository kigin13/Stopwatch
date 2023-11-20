plugins {
    id("stopwatch.lint")
    id("stopwatch.android.library")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.timers.stopwatch.core.data"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.domain)
    implementation(projects.core.dataSource.api)

    implementation(libs.hilt.android)
    implementation(projects.core.log)
    kapt(libs.hilt.compiler)

    implementation(libs.kotlinx.datetime)
}
