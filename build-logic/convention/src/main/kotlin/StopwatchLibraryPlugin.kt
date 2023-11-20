import org.gradle.api.Plugin
import org.gradle.api.Project

class StopwatchLibraryPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.plugins.apply("kotlin")
        project.dependencies.add("implementation", project.libs.kotlinx.coroutines.core.jvm)
    }
}
