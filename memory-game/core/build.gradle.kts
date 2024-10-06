plugins {
    id("com.google.dagger.hilt.android")
    id("io.lb.android.app")
    kotlin("kapt")
}

android {
    namespace = "io.lb.core"

    packaging {
        resources.excludes.add("META-INF/*")
    }
}

dependencies {
    implementation(project(":impl:ktor-client"))
    implementation(project(":impl:room-database"))
    implementation(project(":memory-game:data"))
    implementation(project(":memory-game:domain"))
    implementation(project(":memory-game:presentation"))
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
