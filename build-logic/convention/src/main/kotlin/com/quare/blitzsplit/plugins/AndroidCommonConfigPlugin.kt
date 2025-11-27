package com.quare.blitzsplit.plugins

import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

class AndroidCommonConfigPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        plugins.all {
            when (this) {
                is com.android.build.gradle.LibraryPlugin -> {
                    extensions.configure<LibraryExtension> {
                        configureAndroid(target)
                    }
                }

                is com.android.build.gradle.AppPlugin -> {
                    extensions.configure<AppExtension> {
                        configureAndroid(target)
                    }
                }
            }
        }
    }

    private fun BaseExtension.configureAndroid(project: Project) {
        compileSdkVersion(project.property("compileSdkVersion").toString().toInt())

        defaultConfig {
            minSdk = project.property("minSdkVersion").toString().toInt()
            consumerProguardFiles("consumer-rules.pro")
        }

        buildTypes {
            getByName("release") {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro",
                )
            }
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_21
            targetCompatibility = JavaVersion.VERSION_21
        }

        project.tasks.withType<KotlinCompile>().configureEach {
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_21)
            }
        }
    }
}
