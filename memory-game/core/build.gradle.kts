plugins {
    id("com.google.dagger.hilt.android")
    id("io.lb.android.app")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    kotlin("kapt")
}

android {
    namespace = "io.lb.pokememory.app"

    defaultConfig {
        versionCode = 603
        versionName = "0.6.3"
    }

    packaging {
        resources.excludes.add("META-INF/*")
    }
}

dependencies {
    implementation(platform("com.google.firebase:firebase-bom:33.5.1"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-crashlytics")
    implementation(project(":impl:ktor-client"))
    implementation(project(":impl:room-database"))
    implementation(project(":memory-game:data"))
    implementation(project(":memory-game:domain"))
    implementation(project(":memory-game:presentation"))
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
