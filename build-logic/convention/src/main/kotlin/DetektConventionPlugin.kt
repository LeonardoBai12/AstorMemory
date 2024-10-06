import extensions.detektPlugins
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import provider.libs

/**
 * Plugin to apply Detekt conventions.
 */
class DetektConventionPlugin : Plugin<Project> {
    /**
     * Applies the Detekt conventions to the project.
     *
     * @param target The project to apply the conventions to.
     */
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("io.gitlab.arturbosch.detekt")

            dependencies {
                detektPlugins(libs.findLibrary("detekt-formatting").get())
            }
        }
    }
}
