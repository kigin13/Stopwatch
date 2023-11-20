import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class StopwatchAndroidFeaturePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("com.android.library")
                apply("kotlin-android")
                apply("stopwatch.hilt")
            }
            extensions.configure(BaseExtension::class.java) {
                commonAndroid(target)
                buildFeatures.viewBinding = true
            }

            dependencies.apply {
                add("implementation", project(":core:model"))
                add("implementation", project(":core:domain"))
                add("implementation", project(":core:common-android"))
                add("implementation", libs.androidx.navigation.fragment.ktx)
                add("implementation", libs.material)
            }
        }
    }
}
