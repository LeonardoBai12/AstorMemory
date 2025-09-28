import org.gradle.initialization.DependenciesAccessors
import org.gradle.kotlin.dsl.support.serviceOf
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "io.lb.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.detekt.gradlePlugin)
    compileOnly(libs.dokka.gradlePlugin)
    // The line below allows us to access the libs from version catalog directly in the plugins
    gradle.serviceOf<DependenciesAccessors>().classes.asFiles.forEach {
        compileOnly(files(it.absolutePath))
    }
}

gradlePlugin {
    plugins {
        register("JvmLibraryConventionPlugin") {
            id = "io.lb.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }
        register("DetektConventionPlugin") {
            id = "io.lb.detekt"
            implementationClass = "DetektConventionPlugin"
        }
        register("DokkaModuleConventionPlugin") {
            id = "io.lb.dokka"
            implementationClass = "DokkaModuleConventionPlugin"
        }
        register("JacocoJvmConventionPlugin") {
            id = "io.lb.jacoco.jvm.module"
            implementationClass = "JacocoJvmConventionPlugin"
        }
        register("JacocoAndroidConventionPlugin") {
            id = "io.lb.jacoco.android.module"
            implementationClass = "JacocoAndroidConventionPlugin"
        }
        register("JacocoMultiModuleConventionPlugin") {
            id = "io.lb.jacoco.multi-module"
            implementationClass = "JacocoMultiModuleConventionPlugin"
        }
        register("AndroidLibraryConventionPlugin") {
            id = "io.lb.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("AndroidAppConventionPlugin") {
            id = "io.lb.android.app"
            implementationClass = "AndroidAppConventionPlugin"
        }
    }
}
