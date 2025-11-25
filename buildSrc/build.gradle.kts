plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(libs.android.gradle)
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.compose.gradle.plugin)
}
