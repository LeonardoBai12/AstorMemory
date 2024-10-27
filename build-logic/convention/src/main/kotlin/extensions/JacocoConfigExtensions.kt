package extensions

import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.gradle.testing.jacoco.plugins.JacocoTaskExtension
import org.gradle.testing.jacoco.tasks.JacocoCoverageVerification
import org.gradle.testing.jacoco.tasks.JacocoReport
import org.gradle.testing.jacoco.tasks.JacocoReportBase

private const val JACOCO_VERSION = "0.8.12"
private const val COVERAGE_TASKS_GROUP = "reporting"
private const val MINIMUM_COVERAGE = 0.50

internal fun JacocoCoverageVerification.setupJacocoCoverageVerification(
    minimumCoverage: Double = MINIMUM_COVERAGE
) {
    group = COVERAGE_TASKS_GROUP
    description = "Generate Jacoco coverage reports for the debug build."
    setupViolationRules(minimumCoverage)
}

internal fun JacocoReport.setupJacocoCoverageReport() {
    group = COVERAGE_TASKS_GROUP
    description = "Generate Jacoco verification and coverage reports for the debug build."
    setupReports()
}

internal fun Project.applyJaCoCo() {
    pluginManager.apply("jacoco")
    extensions.configure<JacocoPluginExtension> {
        toolVersion = JACOCO_VERSION
    }
    tasks.withType<Test> {
        extensions.configure(JacocoTaskExtension::class.java) {
            isIncludeNoLocationClasses = true
            excludes = listOf("jdk.internal.*")
        }
    }
}

internal fun Project.setDirectories(
    jacocoReport: JacocoReportBase
) {
    jacocoReport.apply {
        executionData.setFrom(
            files(
                fileTree(layout.buildDirectory) { include(listOf("**/*.exec", "**/*.ec")) }
            )
        )
        classDirectories.setFrom(
            files(
                fileTree(layout.buildDirectory.dir("intermediates/javac/")) {
                    exclude(EXCLUSION_LIST)
                },
                fileTree(layout.buildDirectory.dir("tmp/kotlin-classes/")) {
                    exclude(EXCLUSION_LIST)
                }
            )
        )
        sourceDirectories.setFrom(layout.projectDirectory.dir("src/main"))
    }
}

internal fun Project.setProjectDirectories(jacocoReport: JacocoReportBase) {
    val allExecutionData = project.subprojects.map { subproject ->
        project.fileTree(subproject.layout.buildDirectory) {
            include("**/*.exec", "**/*.ec")
        }
    }
    jacocoReport.executionData.setFrom(project.files(allExecutionData))
    val allClassDirs = project.subprojects.map { subproject ->
        project.files(
            project.fileTree(subproject.layout.buildDirectory.dir("intermediates/javac/")) {
                exclude(EXCLUSION_LIST)
            },
            project.fileTree(subproject.layout.buildDirectory.dir("tmp/kotlin-classes/")) {
                exclude(EXCLUSION_LIST)
            }
        )
    }
    jacocoReport.classDirectories.setFrom(project.files(allClassDirs))
    val allSourceDirs = project.subprojects.map { subproject ->
        subproject.layout.projectDirectory.dir("src/main")
    }
    jacocoReport.sourceDirectories.setFrom(project.files(allSourceDirs))
}

private fun JacocoReport.setupReports() {
    reports {
        html.required.set(true)
        csv.required.set(false)
        xml.required.set(false)
    }
}

private fun JacocoCoverageVerification.setupViolationRules(
    minimumCoverage: Double = MINIMUM_COVERAGE
) {
    violationRules {
        rule {
            limit {
                minimum = minimumCoverage.toBigDecimal()
            }
        }
    }
}

private val EXCLUSION_LIST = listOf(
    "**/R.class",
    "**/R\$*.class",
    "**/BuildConfig.*",
    "**/Manifest*.*",
    "**/*Database.*",
    "**/model/**/*.*",
    "**/client/**/*.*",
    "**/dao/**/*.*",
    "**/**hilt**/**/*.*",
    "**/di/**/*.*",
    "**/ui/**/*.*",
    "**/**Hilt**.*",
    "**/**Activity*.*",
    "**/**Database_Impl**.*",
    "**/**ScreenKt**.*",
    "**/**Application.*",
    "android/**/*.*",
    "androidx/**/*.*",
    "**/*\$ViewInjector*.*",
    "**/*Dagger*.*",
    "**/*MembersInjector*.*",
    "**/*_Factory.*",
    "**/*_Provide*Factory*.*",
    "**/*_ViewBinding*.*",
    "**/AutoValue_*.*",
    "**/R2.class",
    "**/R2\$*.class",
    "**/*Directions\$*",
    "**/*Directions.*",
    "**/*Binding.*"
)
