import extensions.applyJaCoCo
import extensions.isJvm
import extensions.setProjectDirectories
import extensions.setupJacocoCoverageReport
import extensions.setupJacocoCoverageVerification
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.testing.jacoco.tasks.JacocoCoverageVerification
import org.gradle.testing.jacoco.tasks.JacocoReport

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
            applyJaCoCo()
            afterEvaluate {
                tasks.register("jacocoProjectCoverageReport", JacocoReport::class.java) {
                    setupJacocoCoverageReport()
                    subprojects.forEach { subproject ->
                        if (isJvm()) {
                            dependsOn(subproject.tasks.matching { it.name == "test" })
                        } else {
                            dependsOn(subproject.tasks.matching { it.name == "testDebugUnitTest" })
                        }
                    }
                    setProjectDirectories(this)
                }
                tasks.register(
                    "jacocoProjectCoverageVerification",
                    JacocoCoverageVerification::class.java
                ) {
                    setupJacocoCoverageVerification(CURRENT_COVERAGE)
                    dependsOn("jacocoProjectCoverageReport")
                    setProjectDirectories(this)
                }
            }
        }
    }

    private companion object {
        const val CURRENT_COVERAGE = 0.65
    }
}
