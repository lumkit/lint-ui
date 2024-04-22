# Lint UI for Compose Desktop

[![License](https://img.shields.io/github/license/lumkit/lint-ui)](LICENSE)
[![Version](https://img.shields.io/github/v/release/lumkit/lint-ui?include_prereleases)](https://github.com/Konyaco/compose-fluent-ui/releases)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.lumkit/lint-compose-ui)](https://central.sonatype.com/artifact/io.github.lumkit/lint-compose-ui/)

A Compose Desktop UI framework supporting global theme control. (aka LintUI)

## Introduce

This is a UI framework developed for Compose Desktop (on Material Design 3),
and it integrates many features that normal applications
should have (such as data persistence).
This UI framework will remain open source and free.

## Features

- [x] Support the Root Panel covering the whole window (Lint Window)
- [x] Material Design3 color matching style (Lint Theme Scope)
- [x] Commonly used widgets
    - [x] Buttons (Lint Button)
    - [x] Cards (Lint Card)
    - [x] Dividers (Lint Dividers)
    - [x] Folded container (Lint Folder)
    - [x] Minimizable, collapsible and nestable side navigation bar (Lint Side)
    - [ ] More beautiful and practical UI components will continue to be developed in the future
- [x] A simple context provider
- [x] Shared Preferences based on SQLite (SharedPreferences)
- [x] Out of the box theme management store (Lint Theme Store)
- [x] Out-of-box theme installation framework
- [x] Unify the global theme
- [x] Dynamic perception system dark mode
- [ ] More features will be continuously updated in the future

## Screen shoot

![main-light.png](static/img/main-light.png)
![main-dark.png](static/img/main-dark.png)

## Use this library in your project

### LintUI framework version requirements

| Lint UI | Kotlin | Compose Framework | Compose Plugin |   Java   |
|:-------:|:------:|:-----------------:|:--------------:|:--------:|
|  1.0.1  | 1.9.22 |       1.6.2       |     1.6.0      | JDK 11++ |
|  1.0.2  | 1.9.22 |       1.6.2       |     1.6.0      | JDK 11++ |

### 1. Configure the Maven central warehouse for the project.

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}
```

### 2. Import the [lint-compose-ui] dependency.

```kotlin
dependencies {
    // You only need to import dependencies such as jb-compose-desktop-currentOs and jb-compose-components-resources.
    implementation("io.github.lumkit:lint-compose-ui:1.0.1")
}
```

### 3. Start writing your first desktop application

```kotlin
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.MenuBar
import io.github.lumkit.desktop.context.LocalContext
import io.github.lumkit.desktop.data.DarkThemeMode
import io.github.lumkit.desktop.example.App
import io.github.lumkit.desktop.lintApplication
import io.github.lumkit.desktop.ui.LintRememberWindow
import io.github.lumkit.desktop.ui.theme.AnimatedLintTheme
import io.github.lumkit.desktop.ui.theme.LocalThemeStore
import lint_ui.example.generated.resources.*
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import java.awt.Dimension

fun main() = lintApplication(
    packageName = "LintUIExample"
) {
    val context = LocalContext.current

    LintRememberWindow(
        onCloseRequest = ::exitApplication,
        title = context.getPackageName(),
        icon = painterResource(Res.drawable.compose_multiplatform)
    ) {
        window.minimumSize = Dimension(800, 600)
        AnimatedLintTheme(
            modifier = Modifier.fillMaxSize(),
        ) {
            Text("Hello Lint UI")
        }

        // Toggles the global dark theme mode.
        val themeMode = LocalThemeStore.current
        MenuBar {
            Menu(stringResource(Res.string.text_dark_theme)) {
                DarkThemeMode.entries.forEach { entry ->
                    CheckboxItem(
                        when (entry) {
                            DarkThemeMode.SYSTEM -> stringResource(Res.string.text_theme_system)
                            DarkThemeMode.LIGHT -> stringResource(Res.string.text_theme_light)
                            DarkThemeMode.DARK -> stringResource(Res.string.text_theme_dark)
                        },
                        checked = themeMode.darkTheme == entry
                    ) {
                        themeMode.darkTheme = entry
                    }
                }
            }
        }
    }
}
```

## How to use some built-in APIs.

* Context
   ```kotlin
    // Get global context
    val context = LocalContext.current

    // App work path
    val filesDir = context.getFilesDir()

    // App package name
    val packageName = context.getPackageName()

    // Persistent file name of App (based on sqlite implementation)
    val sharedPreferences = context.getSharedPreferences("shared preference file name")

    // Gets the theme instance in the specified persistence file.
    val theme = context.getTheme(sharedPreferences = sharedPreferences)
   ```
* Shared Preferences
   ```kotlin
    // Gets the global SharedPreferences instance.
    val sharedPreferences = LocalSharedPreferences.current

    // Get the data in the hardware (is-dark is virtual).
    val isDark = sharedPreferences.get<String>("is-dark")

    // Put string
    sharedPreferences.putString("is-dark", isDark)
    // Put type data
    sharedPreferences.put("type-data", arrayListOf("Dark", "Light"))
   ```
* More built-in APIs will be gradually opened, so stay tuned

## Excellent cited framework or repositories (Rank insensitive)

* [FlatLat](https://github.com/JFormDesigner/FlatLaf)
* [Exposed](https://github.com/JetBrains/Exposed)
* [Gson](https://github.com/google/gson)
* [JSystemThemeDetector](https://github.com/Dansoftowner/jSystemThemeDetector)

## End