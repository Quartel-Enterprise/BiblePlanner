plugins {
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeMultiplatformConvention)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Compose
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
        }
    }
}

android {
    namespace = "com.quare.bibleplanner.ui.theme"
}
