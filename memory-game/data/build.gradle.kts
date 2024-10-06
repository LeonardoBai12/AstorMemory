plugins {
    id("com.google.dagger.hilt.android")
    id("io.lb.android.library")
    kotlin("kapt")
}

android {
    namespace = "io.lb.data"
}

dependencies {
    implementation(project(":memory-game:data"))
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
