import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.vanniktech.maven.publish)
    alias(libs.plugins.kotlinxSerialization)
}

group = "io.github.lumkit"
version = libs.versions.lint.compose.ui.get()

kotlin {
    jvm("desktop")
    sourceSets {
        val desktopMain by getting

        commonMain.dependencies {
            api(compose.runtime)
            api(compose.foundation)
            api(compose.ui)
            api(compose.components.resources)
            api(compose.components.uiToolingPreview)
            api(compose.animation)
            api(compose.animationGraphics)
            api(compose.material3)
            api(compose.runtimeSaveable)
            api(compose.preview)
            api(compose.materialIconsExtended)
            api(compose.uiTooling)
            api(compose.uiUtil)
            api(libs.kotlinx.serialization.json)

        }
        desktopMain.dependencies {
            api(compose.desktop.common)

            //sql
            api(libs.exposed.core)
            api(libs.exposed.crypt)
            api(libs.exposed.dao)
            api(libs.exposed.java.time)
            api(libs.exposed.jdbc)
            api(libs.exposed.json)
            api(libs.exposed.kotlin.datetime)
            api(libs.sqlite.jdbc)

            api(libs.j.system.theme.detector)
            api(libs.flat.lat)
        }
    }
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, automaticRelease = true)
    signAllPublications()

    allprojects.forEach { project ->
        project.afterEvaluate {
            project.extensions.findByType(PublishingExtension::class.java)?.apply {
                project.extensions.findByType(SigningExtension::class.java)?.apply {
                    useGpgCmd()
                    publishing.publications.withType(MavenPublication::class.java).forEach { publication ->
                        sign(publication)
                    }
                }
            }
        }
    }

    coordinates(
        groupId = "io.github.lumkit",
        artifactId = "lint-compose-ui",
        version = libs.versions.lint.compose.ui.get()
    )

    pom {
        name.set("lint-compose-ui")
        description.set("A Compose Desktop UI framework supporting global theme control. (aka LintUI)")
        url.set("https://github.com/lumkit/lint-ui")

        licenses {
            license {
                name.set("GNU LESSER GENERAL PUBLIC LICENSE, Version 2.1")
                url.set("https://www.gnu.org/licenses/old-licenses/lgpl-2.1.txt")
            }
        }

        developers {
            developer {
                name.set("lumkit")
                email.set("2205903933@qq.com")
                url.set("https://github.com/lumkit")
            }
        }

        scm {
            url.set("https://github.com/lumkit/lint-ui")
            connection.set("scm:git:git://github.com/lumkit/lint-ui.git")
            developerConnection.set("scm:git:ssh://github.com/lumkit/lint-ui.git")
        }
    }
}