plugins {
    id("com.google.dagger.hilt.android")
    id("io.lb.android.library")
    kotlin("kapt")
}

android {
    namespace = "io.lb.impl.ktor.client"
}

dependencies {
    implementation(project(":common:shared"))
    implementation(project(":common:data"))
    implementation(libs.hilt.android)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.logback.classic)
    testImplementation(libs.ktor.client.mock)
    testImplementation(libs.junit.jupiter.api)
    kapt(libs.hilt.compiler)
}
