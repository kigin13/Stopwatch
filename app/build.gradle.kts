plugins {
    id("stopwatch.lint")
    id("stopwatch.android.application")
    id("stopwatch.hilt")
    kotlin("kapt")
}

android {
    signingConfigs {
        maybeCreate("staging").apply {
            keyAlias = "key1"
            keyPassword = "staging007"
            storeFile = file("keystore/staging.jks")
            storePassword = "staging007"
        }
    }
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.data)
    implementation(projects.core.commonAndroid)
    implementation(projects.core.dataSource.api)
    implementation(projects.core.dataSource.impl)
    implementation(projects.core.model)
    implementation(projects.core.log)

    implementation(projects.feature.pomodoro)

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.lifecycle.process)
    implementation(libs.androidx.navigation.fragment.ktx)

    implementation(libs.timber)

    debugImplementation(libs.leakcanary)
}
