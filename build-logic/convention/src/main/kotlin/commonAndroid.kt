import com.android.build.gradle.BaseExtension
import com.timers.stopwatch.buildlogic.ApkConfig
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * Created by Andriy Deputat email(andriy.deputat@gmail.com) on 21.11.2022.
 */
fun BaseExtension.commonAndroid(target: Project) {
    configureDefaultConfig()
    configureBuildTypes()
    configureBuildFeatures()
    configureCompileOptions()

    target.suppressOptIn()
}

private fun BaseExtension.configureDefaultConfig() {
    compileSdkVersion(ApkConfig.COMPILE_SDK_VERSION)
    defaultConfig {
        minSdk = ApkConfig.MIN_SDK_VERSION
        targetSdk = ApkConfig.TARGET_SDK_VERSION

        consumerProguardFiles("consumer-rules.pro")

        testOptions {
            unitTests {
                isIncludeAndroidResources = true
                isReturnDefaultValues = true
            }
        }
    }
}

private fun BaseExtension.configureBuildTypes() {
    buildTypes {
        maybeCreate("debug").apply {
            isDebuggable = true
            isMinifyEnabled = false
        }
        maybeCreate("staging").apply {
            isDebuggable = false
            isMinifyEnabled = true
            consumerProguardFile("proguard-rules.pro")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
        maybeCreate("release").apply {
            isDebuggable = false
            isMinifyEnabled = true
            consumerProguardFile("proguard-rules.pro")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
}

private fun BaseExtension.configureBuildFeatures() {
    buildFeatures.buildConfig = true
    // Disable by default. ViewBinding needed only for few modules.
    // No need to enable this feature for all modules.
    buildFeatures.viewBinding = false
}

private fun BaseExtension.configureCompileOptions() {
    compileOptions.sourceCompatibility = JavaVersion.VERSION_17
    compileOptions.targetCompatibility = JavaVersion.VERSION_17
}

private fun Project.suppressOptIn() {
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_17.toString()
        }
    }
}
