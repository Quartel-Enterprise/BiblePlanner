plugins {
    id("compose-multiplatform-convention")
    alias(libs.plugins.composeCompiler)
}

android {
    namespace = "quare.software.bibleplanner.core.navigation"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Add navigation library dependency here when needed
            // e.g., Voyager, Decompose, or Compose Navigation
        }
    }
}

