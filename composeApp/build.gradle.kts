import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.applicationConvention)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
}

// Override namespace and applicationId
android {
    namespace = "com.quare.bibleplanner"
    defaultConfig {
        applicationId = "com.quare.bibleplanner"
    }
}

// Add project-specific dependencies
kotlin {
    sourceSets {
        commonMain.dependencies {
            // Theme
            implementation(projects.ui.theme)
        }
    }
}

// Override compose.desktop configuration
compose.desktop {
    application {
        mainClass = "com.quare.bibleplanner.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.quare.bibleplanner"
            packageVersion = "1.0.0"
        }
    }
}
