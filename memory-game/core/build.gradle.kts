plugins {
    id("com.google.dagger.hilt.android")
    id("io.lb.android.app")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    kotlin("kapt")
}

android {
    namespace = "io.lb.astormemory.app"

    defaultConfig {
        versionCode = 5
        versionName = "1.1.2"
    }

    packaging {
        resources.excludes.add("META-INF/*")
    }
}

dependencies {
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.integrity)
    implementation(project(":impl:room-database"))
    implementation(project(":memory-game:data"))
    implementation(project(":memory-game:domain"))
    implementation(project(":memory-game:presentation"))
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
