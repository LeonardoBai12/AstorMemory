import extensions.applyJaCoCo
import extensions.setDirectories
import extensions.setupJacocoCoverageReport
import extensions.setupJacocoCoverageVerification
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.configurationcache.extensions.capitalized
import org.gradle.testing.jacoco.tasks.JacocoCoverageVerification
import org.gradle.testing.jacoco.tasks.JacocoReport

/**
 * Plugin to apply Jacoco module conventions.
 */
class JacocoAndroidConventionPlugin : Plugin<Project> {
    /**
     * Applies the Jacoco module conventions to the project.
     *
     * @param target The project to apply the conventions to.
     */
    override fun apply(target: Project) {
        with(target) {
            applyJaCoCo()

            afterEvaluate {
                val module = project.name
                val testCoverageTaskName = "jacoco${module.capitalized()}CoverageReport"
                val verificationTaskName = "jacoco${module.capitalized()}CoverageVerification"

                tasks.register(verificationTaskName, JacocoCoverageVerification::class.java) {
                    setupJacocoCoverageVerification()
                    dependsOn(testCoverageTaskName)
                    setDirectories(this)
                }

                tasks.register(testCoverageTaskName, JacocoReport::class.java) {
                    setupJacocoCoverageReport()
                    dependsOn("testDebugUnitTest")
                    setDirectories(this)
                }
            }
        }
    }
}
