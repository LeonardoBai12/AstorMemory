plugins {
    id("com.google.dagger.hilt.android")
    id("io.lb.android.library")
    kotlin("kapt")
}

android {
    namespace = "io.lb.impl.room.database"
}

dependencies {
    implementation(project(":common:shared"))
    implementation(project(":common:data"))
    implementation(libs.hilt.android)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.hilt.compiler)
    kapt(libs.room.compiler)
}