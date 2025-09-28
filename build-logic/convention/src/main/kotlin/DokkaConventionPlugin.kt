import java.io.File
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.dokka.DokkaConfiguration
import org.jetbrains.dokka.gradle.DokkaTaskPartial

/**
 * Plugin to apply Dokka module conventions.
 */
class DokkaConventionPlugin : Plugin<Project> {
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
                    // Configure module naming based on project path
                    val relativePath = project.projectDir
                        .relativeTo(rootProject.projectDir)
                        .path
                        .replace(File.separator, ":")
                    moduleName.set(relativePath)

                    // Enable reporting of undocumented code
                    reportUndocumented.set(true)

                    // Suppress documentation based on packages names
                    perPackageOption {
                        matchingRegex.set(".*di.*")
                        suppress.set(true)
                    }

                    // Skips classes marked as @Deprecated
                    skipDeprecated.set(true)

                    // Sets the visibility of documented elements
                    documentedVisibilities.set(
                        setOf(
                            DokkaConfiguration.Visibility.PUBLIC,
                            DokkaConfiguration.Visibility.INTERNAL,
                            DokkaConfiguration.Visibility.PRIVATE
                        )
                    )

                    // Include module-specific documentation files
                    if (file("Packages.md").exists()) {
                        includes.from("Packages.md")
                    }
                    if (file("Module.md").exists()) {
                        includes.from("Module.md")
                    }

                    // Skips empty packages, even though they are documented in Packages.md
                    skipEmptyPackages.set(true)

                    // Configure which code visibility levels to document
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
