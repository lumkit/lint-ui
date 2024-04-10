import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
}

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
            implementation(compose.desktop.currentOs)
            implementation(project(":lint-compose-ui"))
        }
    }
}


compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb, TargetFormat.Exe)
            packageName = "io.github.lumkit.lint"
            packageVersion = "1.0.1"
        }
    }
}
