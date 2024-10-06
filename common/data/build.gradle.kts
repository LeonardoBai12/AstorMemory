import extensions.implementation

plugins {
    id("io.lb.jvm.library")
}
dependencies {
    implementation(project(":common:shared"))
    implementation(libs.kotlinx.serialization.json)
}
