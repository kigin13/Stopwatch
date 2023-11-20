plugins {
    id("stopwatch.lint")
    id("stopwatch.library")
}
dependencies {
    implementation(projects.core.model)
    implementation(libs.kotlinx.coroutines.core.jvm)
}
