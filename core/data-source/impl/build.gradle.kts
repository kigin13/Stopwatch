plugins {
    id("stopwatch.lint")
    id("stopwatch.android.library")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}
android {
    namespace = "com.timers.stopwatch.core.data.source.impl"
}

dependencies {
    implementation(projects.core.dataSource.api)
    implementation(projects.core.database)
    implementation(projects.core.preference)
    implementation(projects.core.model)
    implementation(projects.core.domain)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
