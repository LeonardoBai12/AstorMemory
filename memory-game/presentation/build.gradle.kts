plugins {
    id("com.google.dagger.hilt.android")
    id("io.lb.android.app")
    kotlin("kapt")
}

android {
    namespace = "io.lb.presentation"

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.13"
    }
}

dependencies {
    implementation(project(":memory-game:domain"))
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
