plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
}

group = "io.github.lumkit"
version = "1.0.1-SNAPSHOT"

kotlin {
    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(compose.animation)
            implementation(compose.animationGraphics)
            implementation(compose.material3)
            implementation(compose.runtimeSaveable)
            implementation(compose.preview)
            implementation(compose.materialIconsExtended)
            implementation(compose.uiTooling)
            implementation(compose.uiUtil)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.common)

            //sql
            implementation(libs.exposed.core)
            implementation(libs.exposed.crypt)
            implementation(libs.exposed.dao)
            implementation(libs.exposed.java.time)
            implementation(libs.exposed.jdbc)
            implementation(libs.exposed.json)
            implementation(libs.exposed.kotlin.datetime)
            implementation(libs.sqlite.jdbc)

            //gson
            implementation(libs.gson)

            implementation(libs.j.system.theme.detector)
            implementation(libs.flat.lat)
        }
    }
}