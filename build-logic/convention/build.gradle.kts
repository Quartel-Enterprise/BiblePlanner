plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation(libs.android.gradle)
    implementation(libs.kotlin.gradle.plugin)
}

gradlePlugin {
    plugins {
        create("androidCommonConfig") {
            id = "com.quare.android.common"
            implementationClass = "com.quare.blitzsplit.plugins.AndroidCommonConfigPlugin"
        }
    }
}
