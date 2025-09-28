import com.android.build.api.dsl.LibraryExtension
import extensions.androidTestImplementation
import extensions.configureKotlinAndroid
import extensions.debugImplementation
import extensions.implementation
import extensions.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import provider.libs

/**
 * Plugin to apply Android library conventions.
 */
class AndroidLibraryConventionPlugin : Plugin<Project> {
    /**
     * Applies the Android library conventions to the project.
     *
     * @param target The project to apply the conventions to.
     */
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.plugin.serialization")
                apply("org.jetbrains.kotlin.android")
                apply("org.jetbrains.kotlin.plugin.compose")
                apply("io.lb.jacoco.android.module")
                apply("io.lb.detekt")
                apply("io.lb.dokka")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
            }

            dependencies {
                with(libs) {
                    implementation(androidx.core.ktx)
                    implementation(androidx.lifecycle.runtime.ktx)
                    implementation(androidx.activity.compose)
                    implementation(platform(androidx.compose.bom))
                    implementation(androidx.ui.compose)
                    implementation(androidx.ui.graphics)
                    implementation(androidx.ui.tooling.preview)
                    implementation(androidx.material3)
                    implementation(kotlinx.coroutines.android)
                    testImplementation(mockk)
                    testImplementation(junit.jupiter.api)
                    testImplementation(junit.jupiter.params)
                    testImplementation(assertk)
                    testImplementation(kotlinx.coroutines.test)
                    testImplementation(kotlin.test.junit)
                    androidTestImplementation(androidx.espresso.core)
                    debugImplementation(androidx.ui.tooling.compose)
                }
            }
        }
    }
}