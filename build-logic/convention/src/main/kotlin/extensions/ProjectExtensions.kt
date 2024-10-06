package extensions

import com.android.build.api.dsl.CommonExtension
import provider.libs
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.withType
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.gradle.testing.jacoco.plugins.JacocoTaskExtension
import org.gradle.testing.jacoco.tasks.JacocoCoverageVerification
import org.gradle.testing.jacoco.tasks.JacocoReport
import org.gradle.testing.jacoco.tasks.JacocoReportBase
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * Configures the Kotlin settings for the project.
 *
 * @receiver The project to configure the Kotlin settings for.
 * @param commonExtension The common extension to configure the Kotlin settings for.
 */
internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        compileSdk =  libs.findVersion("compileSdk").get().toString().toInt()

        defaultConfig {
            minSdk = libs.findVersion("minSdk").get().toString().toInt()
        }

        compileOptions {
            sourceCompatibility = COMPILE_VERSION
            targetCompatibility = COMPILE_VERSION
        }
    }

    configureKotlin()
}

/**
 * Configures the Kotlin settings for the project.
 *
 * @receiver The project to configure the Kotlin settings for.
 */
internal fun Project.configureKotlinJvm() {
    extensions.configure<JavaPluginExtension> {
        sourceCompatibility = COMPILE_VERSION
        targetCompatibility = COMPILE_VERSION
    }

    configureKotlin()
}

/**
 * Configures the Kotlin settings for the project.
 *
 * @receiver The project to configure the Kotlin settings for.
 */
private fun Project.configureKotlin() {
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = COMPILE_VERSION.toString()
            val warningsAsErrors: String? by project
            allWarningsAsErrors = warningsAsErrors.toBoolean()
            freeCompilerArgs = freeCompilerArgs + listOf(
                "-opt-in=kotlin.RequiresOptIn",
                "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-opt-in=kotlinx.coroutines.FlowPreview",
            )
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }
}

/**
 * Configures the Jacoco settings for the project.
 *
 * @receiver The JacocoCoverageVerification to configure the settings for.
 * @param minimumCoverage The minimum coverage percentage to enforce.
 */
internal fun JacocoCoverageVerification.setupCoverageVerification(
    minimumCoverage: Double = 0.80
) {
    violationRules {
        rule {
            limit {
                minimum = minimumCoverage.toBigDecimal()
            }
        }
    }
}

/**
 * Configures the Jacoco settings for the project.
 *
 * @receiver The JacocoReport to configure the settings for.
 */
internal fun JacocoReport.setupCoverageReport() {
    reports {
        xml.apply {
            isEnabled = false
        }
        csv.apply {
            isEnabled = false
        }
        html.apply {
            isEnabled = true
        }
    }
}

/**
 * Sets up the Jacoco plugin for the project.
 *
 * @receiver The project to set up the Jacoco plugin for.
 */
internal fun Project.setupJacoco() {
    pluginManager.apply("jacoco")
    extensions.configure<JacocoPluginExtension> {
        toolVersion = libs.findVersion("jacoco").get().toString()
    }
    tasks.withType<Test> {
        extensions.configure<JacocoTaskExtension> {
            isIncludeNoLocationClasses = true
            excludes = listOf("jdk.internal.*")
        }
    }
}

/**
 * Sets up the Jacoco directories for the project.
 *
 * @receiver The project to set up the Jacoco directories for.
 * @param jacocoReport The JacocoReport to set up the directories for.
 */
internal fun Project.setJacocoJvmDirectories(
    jacocoReport: JacocoReportBase
) {
    jacocoReport.apply {
        executionData.from(fileTree("${layout.buildDirectory}/jacoco/test.exec"))
        setJacocoDirectories(this)
    }
}

/**
 * Sets up the Jacoco directories for the project.
 *
 * @receiver The project to set up the Jacoco directories for.
 * @param jacocoReport The JacocoReport to set up the directories for.
 */
internal fun Project.setJacocoAndroidDirectories(
    jacocoReport: JacocoReportBase
) {
    jacocoReport.apply {
        executionData.from(fileTree("${layout.buildDirectory}/jacoco/testDebugUnitTest.exec"))
        setJacocoDirectories(this)
    }
}

/**
 * Sets up the Jacoco directories for the project.
 *
 * @receiver The project to set up the Jacoco directories for.
 * @param jacocoReport The JacocoReport to set up the directories for.
 */
internal fun Project.setJacocoDirectories(
    jacocoReport: JacocoReportBase
) {
    val fileFilter = listOf(
        "**/build/generated/**",
        "**/Application.kt",
        "**/*Configuration.kt",
        "**/*Kt$*",
        "**/internal/**",
        "**/R.class",
        "**/BuildConfig.*",
        "**/session/**",
        "**/model/**",
        "**/plugins/**",
        "**/di/**",
        "**/security/**",
    )

    jacocoReport.apply {
        val javaTree = fileTree(
            mapOf(
                "dir" to "${layout.buildDirectory}/intermediates/javac/debug/classes",
                "excludes" to fileFilter
            )
        )
        val kotlinTree = fileTree(
            mapOf(
                "dir" to "${layout.buildDirectory}/tmp/kotlin-classes/debug",
                "excludes" to fileFilter
            )
        )

        classDirectories.setFrom(files(javaTree, kotlinTree))

        val sourceDirs = listOf(
            "src/main/kotlin",
            "src/main/java",
            "src/debug/kotlin",
            "src/debug/java"
        )
        sourceDirectories.setFrom(files(sourceDirs))
        additionalSourceDirs.setFrom(files(sourceDirs))
    }
}

/**
 * Determines if the project is a JVM project.
 *
 * @receiver The project to determine if it is a JVM project.
 * @return True if the project is a JVM project, false otherwise.
 */
fun Project.isJvm(): Boolean {
    return this.plugins.hasPlugin("io.lb.jvm.library")
}
