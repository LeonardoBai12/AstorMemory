import java.io.File
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.dokka.DokkaConfiguration
import org.jetbrains.dokka.gradle.DokkaTaskPartial

/**
 * Plugin to apply Dokka module conventions.
 */
class DokkaModuleConventionPlugin : Plugin<Project> {
    /**
     * Applies the Dokka module conventions to the project.
     *
     * @param target The project to apply the conventions to.
     */
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("org.jetbrains.dokka")

            tasks.withType<DokkaTaskPartial> {
                dokkaSourceSets.configureEach {
                    val relativePath = project.projectDir
                        .relativeTo(rootProject.projectDir)
                        .path
                        .replace(File.separator, ":")
                    moduleName.set(relativePath)

                    reportUndocumented.set(true)

                    perPackageOption {
                        matchingRegex.set(".*di.*")
                        suppress.set(true)
                    }

                    if (file("Packages.md").exists()) {
                        includes.from("Packages.md")
                    }
                    if (file("Module.md").exists()) {
                        includes.from("Module.md")
                    }
                    documentedVisibilities.set(
                        setOf(
                            DokkaConfiguration.Visibility.PUBLIC,
                            DokkaConfiguration.Visibility.INTERNAL
                        )
                    )
                }
            }
        }
    }
}
