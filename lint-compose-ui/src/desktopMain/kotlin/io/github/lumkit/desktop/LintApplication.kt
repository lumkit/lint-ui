package io.github.lumkit.desktop

import androidx.compose.runtime.*
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.application
import com.jthemedetecor.OsThemeDetector
import io.github.lumkit.desktop.annotates.MODE_FILES
import io.github.lumkit.desktop.context.Context
import io.github.lumkit.desktop.context.LocalContext
import io.github.lumkit.desktop.data.DarkThemeMode
import io.github.lumkit.desktop.preferences.LocalSharedPreferences
import io.github.lumkit.desktop.preferences.SharedPreferences
import io.github.lumkit.desktop.ui.theme.LintTheme
import io.github.lumkit.desktop.ui.theme.LocalLintTheme
import io.github.lumkit.desktop.ui.theme.LocalThemeStore
import io.github.lumkit.desktop.ui.theme.ThemeStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.io.File

/**
 * An entry point for the Compose application.
 * @see androidx.compose.ui.window.application
 */
fun lintApplication(
    packageName: String = "Lint UI",
    exitProcessOnExit: Boolean = true,
    content: @Composable ApplicationScope.() -> Unit
) = application(exitProcessOnExit = exitProcessOnExit) {

    val context = object : Context() {
        override fun getPackageName(): String = packageName

        override fun getDir(name: String): File = File(appDir, name).apply {
            if (!exists()) mkdirs()
        }

        override fun getFilesDir(): File = getDir(MODE_FILES).apply {
            if (!exists()) mkdirs()
        }

        override fun getSharedPreferences(name: String): SharedPreferences = SharedPreferences(this, name)

        override fun getTheme(sharedPreferences: SharedPreferences): LintTheme = LintTheme(
            context = this,
            sharedPreferences = sharedPreferences,
        )
    }

    val sharedPreferences = context.getSharedPreferences(Const.APP_CONFIGURATION)
    val lintTheme = context.getTheme()

    val themeStore = remember { ThemeStore() }

    // watch system dark theme change
    themeStore.isSystemDarkTheme = OsThemeDetector.getDetector().isDark
    OsThemeDetector.getDetector().registerListener { isDarkThemeEnabled ->
        if (themeStore.darkTheme == DarkThemeMode.SYSTEM) {
            themeStore.isSystemDarkTheme = isDarkThemeEnabled
        }
    }

    // Initialize dark theme mode
    try {
        themeStore.darkTheme = DarkThemeMode.valueOf(sharedPreferences.getString(Const.DARK_THEME_MODE).toString())
    }catch (_: Exception) {}

    // Initialize color scheme
    try {
        themeStore.colorSchemes = lintTheme.getUsedTheme()
    }catch (_: Exception) {}

    LaunchedEffect(Unit) {
        // Persistent theme color configuration
        snapshotFlow { themeStore.darkTheme }
            .onEach {
                sharedPreferences.putString(Const.DARK_THEME_MODE, it.name)
            }.flowOn(Dispatchers.IO)
            .launchIn(this)

        snapshotFlow { themeStore.colorSchemes }
            .onEach {
                sharedPreferences.putString(Const.USED_THEME_FILE_NAME, it.label)
            }.flowOn(Dispatchers.IO)
            .launchIn(this)
    }

    CompositionLocalProvider(
        LocalContext provides context,
        LocalSharedPreferences provides sharedPreferences,
        LocalLintTheme provides lintTheme,
        LocalThemeStore provides themeStore,
    ) {
        content()
    }
}