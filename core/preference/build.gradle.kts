import com.google.protobuf.gradle.id

plugins {
    id("stopwatch.lint")
    id("stopwatch.android.library")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.protobuf")
}

android {
    namespace = "com.timers.stopwatch.core.preference"
}

dependencies {
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    api(libs.protobuf.kotlinlite)
    api(libs.androidx.dataStore)
}

protobuf {
    protoc {
        artifact = libs.protobuf.protoc.get().toString()
    }

    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                id("java") {
                    option("lite")
                }
                id("kotlin")
            }
        }
    }
}
