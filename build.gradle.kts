// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript { // przed blokiem plugins
    repositories {
        mavenCentral()
    }

    dependencies {
        val nav_version = "2.8.0"

        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7")

    }
}
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
}