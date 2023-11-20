plugins {
    id("stopwatch.lint")
    id("stopwatch.android.library")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.timers.stopwatch.core.common.android"

    buildFeatures.viewBinding = true
}

dependencies {
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.material)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.ssp)
    implementation(libs.sdp)

    implementation(projects.core.model)
    implementation(projects.core.log)
}
