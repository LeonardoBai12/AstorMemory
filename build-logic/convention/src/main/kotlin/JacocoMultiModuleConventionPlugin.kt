import extensions.isJvm
import extensions.setJacocoAndroidDirectories
import extensions.setJacocoJvmDirectories
import extensions.setupCoverageReport
import extensions.setupCoverageVerification
import extensions.setupJacoco
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.configurationcache.extensions.capitalized
import org.gradle.testing.jacoco.plugins.JacocoCoverageReport
import org.gradle.testing.jacoco.tasks.JacocoCoverageVerification
import org.gradle.testing.jacoco.tasks.JacocoReport
import org.gradle.testing.jacoco.tasks.JacocoReportBase

/**
 * Plugin to apply Jacoco multi-module conventions.
 */
class JacocoMultiModuleConventionPlugin : Plugin<Project> {
    /**
     * Applies the Jacoco multi-module conventions to the project.
     *
     * @param target The project to apply the conventions to.
     */
    override fun apply(target: Project) {
        with(target) {
            val module = project.name.capitalized()
            val report = "jacoco${module}CoverageReport"
            val verification = "jacoco${module}CoverageVerification"

            setupJacoco()

            tasks.register(report, JacocoReport::class.java) {
                group = "verification"
                description = "Generates $module coverage report."
                setupCoverageReport()

                subprojects.forEach { subproject ->
                    if (subproject.isJvm()) {
                        subproject.setJacocoJvmDirectories(this)
                        dependsOn(subproject.tasks.matching { it.name == "test" })
                    } else {
                        subproject.setJacocoAndroidDirectories(this)
                        dependsOn(subproject.tasks.matching { it.name == "testDebugUnitTest" })
                    }
                }
            }

            tasks.register(verification, JacocoCoverageVerification::class.java) {
                group = "verification"
                description = "Validates $module coverage."

                setupCoverageVerification()
                dependsOn(report)

                subprojects.forEach { subproject ->
                    if (subproject.isJvm()) {
                        subproject.setJacocoJvmDirectories(this)
                    } else {
                        subproject.setJacocoAndroidDirectories(this)
                    }
                }
            }
        }
    }
}
