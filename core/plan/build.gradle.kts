plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.androidCommonConfig)
}

kotlin {
    androidTarget()

    listOf(
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "CorePlan"
            isStatic = true
        }
    }

    jvm()
    sourceSets {
        commonMain.dependencies {
            // Core
            implementation(projects.core.model)

            // Coroutines
            implementation(libs.kotlinx.coroutines.core)

            // Serialization
            implementation(libs.kotlin.serialization.json)
        }
    }
}

android {
    namespace = "com.quare.bibleplanner.core.plan"
}
