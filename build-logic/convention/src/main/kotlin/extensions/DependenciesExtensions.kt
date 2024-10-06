package extensions

import org.gradle.api.JavaVersion
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.DependencyHandlerScope

val COMPILE_VERSION = JavaVersion.VERSION_17

/**
 * Adds an implementation dependency to the project.
 *
 * @receiver The dependency handler scope to add the dependency to.
 * @param name The name of the dependency to add.
 */
fun DependencyHandlerScope.implementation(name: Provider<MinimalExternalModuleDependency>) {
    add("implementation", name)
}

/**
 * Adds a test implementation dependency to the project.
 *
 * @receiver The dependency handler scope to add the dependency to.
 * @param name The name of the dependency to add.
 */
fun DependencyHandlerScope.testImplementation(name: Provider<MinimalExternalModuleDependency>) {
    add("testImplementation", name)
}

/**
 * Adds an android test implementation dependency to the project.
 *
 * @receiver The dependency handler scope to add the dependency to.
 * @param name The name of the dependency to add.
 */
fun DependencyHandlerScope.androidTestImplementation(name: Provider<MinimalExternalModuleDependency>) {
    add("androidTestImplementation", name)
}

/**
 * Adds a debug implementation dependency to the project.
 *
 * @receiver The dependency handler scope to add the dependency to.
 * @param name The name of the dependency to add.
 */
fun DependencyHandlerScope.debugImplementation(name: Provider<MinimalExternalModuleDependency>) {
    add("debugImplementation", name)
}

/**
 * Adds a detekt plugin dependency to the project.
 *
 * @receiver The dependency handler scope to add the dependency to.
 * @param name The name of the dependency to add.
 */
fun DependencyHandlerScope.detektPlugins(name: Provider<MinimalExternalModuleDependency>) {
    add("detektPlugins", name)
}
