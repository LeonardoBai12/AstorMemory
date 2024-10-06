import extensions.isJvm
import extensions.setJacocoAndroidDirectories
import extensions.setJacocoJvmDirectories
import extensions.setupCoverageVerification
import extensions.setupJacoco
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.configurationcache.extensions.capitalized
import org.gradle.testing.jacoco.tasks.JacocoCoverageVerification
import org.gradle.testing.jacoco.tasks.JacocoReport

/**
 * Plugin to apply Jacoco module conventions.
 */
class JacocoModuleConventionPlugin : Plugin<Project> {
    /**
     * Applies the Jacoco module conventions to the project.
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

                if (isJvm()) {
                    setJacocoJvmDirectories(this)
                    dependsOn("test")
                } else {
                    setJacocoAndroidDirectories(this)
                    dependsOn("testDebugUnitTest")
                }
            }

            tasks.register(verification, JacocoCoverageVerification::class.java) {
                group = "verification"
                description = "Validates $module coverage."

                setupCoverageVerification()
                dependsOn(report)

                if (isJvm()) {
                    setJacocoJvmDirectories(this)
                } else {
                    setJacocoAndroidDirectories(this)
                }
            }
        }
    }
}
