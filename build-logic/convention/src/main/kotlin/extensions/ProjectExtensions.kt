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
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        compileOptions {
            sourceCompatibility = COMPILE_VERSION
            targetCompatibility = COMPILE_VERSION
        }

        buildTypes {
            getByName("debug") {
                enableUnitTestCoverage = true
            }
            getByName("release") {
                enableUnitTestCoverage = true
            }
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
 * Determines if the project is a JVM project.
 *
 * @receiver The project to determine if it is a JVM project.
 * @return True if the project is a JVM project, false otherwise.
 */
fun Project.isJvm(): Boolean {
    return this.plugins.hasPlugin("io.lb.jvm.library")
}
