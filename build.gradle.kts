plugins {
    // Plugins used in convention plugins are available on the classpath from buildSrc dependencies
    // They don't need to be declared here to avoid version conflicts
    alias(libs.plugins.composeHotReload) apply false
    alias(libs.plugins.composeCompiler) apply false
    // The following plugins are in buildSrc dependencies and applied via convention plugins:
    // - com.android.application / com.android.library
    // - org.jetbrains.kotlin.multiplatform
    // - org.jetbrains.compose
}