plugins {
    id("com.google.dagger.hilt.android")
    id("io.lb.android.library")
    kotlin("kapt")
}

android {
    namespace = "io.lb.domain"
}

dependencies {
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
