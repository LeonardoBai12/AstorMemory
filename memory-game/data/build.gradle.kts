plugins {
    id("com.google.dagger.hilt.android")
    id("io.lb.android.library")
    kotlin("kapt")
}

android {
    namespace = "io.lb.data"
}

dependencies {
    implementation(project(":common:data"))
    implementation(project(":common:shared"))
    implementation(project(":memory-game:domain"))
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    testImplementation(kotlin("test"))
}
