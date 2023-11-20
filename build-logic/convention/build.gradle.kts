import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.timers.stopwatch.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)

    implementation(libs.ktlint.gradle)
    implementation(libs.detekt.gradle)

    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}

gradlePlugin {
    plugins {
        register("stopwatchAndroidApplication") {
            id = "stopwatch.android.application"
            implementationClass = "StopwatchAndroidApplicationPlugin"
        }
        register("stopwatchAndroidFeature") {
            id = "stopwatch.android.feature"
            implementationClass = "StopwatchAndroidFeaturePlugin"
        }
        register("stopwatchHilt") {
            id = "stopwatch.hilt"
            implementationClass = "StopwatchHiltPlugin"
        }
        register("stopwatchAndroidLibrary") {
            id = "stopwatch.android.library"
            implementationClass = "StopwatchAndroidLibraryPlugin"
        }
        register("stopwatchLibrary") {
            id = "stopwatch.library"
            implementationClass = "StopwatchLibraryPlugin"
        }
        register("stopwatchLint") {
            id = "stopwatch.lint"
            implementationClass = "StopwatchLintPlugin"
        }
    }
}
