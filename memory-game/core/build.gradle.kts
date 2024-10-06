plugins {
    id("com.google.dagger.hilt.android")
    id("io.lb.android.app")
    kotlin("kapt")
}

android {
    namespace = "io.lb.core"
}

dependencies {
    implementation(project(":memory-game:presentation"))
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
