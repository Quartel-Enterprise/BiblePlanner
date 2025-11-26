plugins {
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeMultiplatformConvention)
}

android {
    namespace = "com.quare.bibleplanner.core.navigation"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
        }
    }
}
