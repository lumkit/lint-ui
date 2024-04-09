package io.github.lumkit.desktop.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import com.google.gson.Gson
import io.github.lumkit.desktop.Const
import io.github.lumkit.desktop.annotates.MODE_FILES
import io.github.lumkit.desktop.common.toColor
import io.github.lumkit.desktop.context.Context
import io.github.lumkit.desktop.data.DarkThemeMode
import io.github.lumkit.desktop.data.SchemesBasic
import io.github.lumkit.desktop.data.ThemeBean
import io.github.lumkit.desktop.preferences.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.*

val LocalLintTheme = compositionLocalOf<LintTheme> { error("Not provided.") }
val LocalThemeStore = compositionLocalOf<ThemeStore> { error("Not provided.") }
val DefaultLintTheme = LintTheme.LintThemeColorSchemes(
    label = "Default",
    light = lightScheme,
    dark = darkScheme,
)

/**
 * Global theme color status container.
 */
class ThemeStore {
    var darkTheme by mutableStateOf(DarkThemeMode.SYSTEM)
    var colorSchemes by mutableStateOf(
        DefaultLintTheme
    )
    var isSystemDarkTheme by mutableStateOf(false)
    var isDarkTheme by mutableStateOf(false)
}

class LintTheme internal constructor(
    private val context: Context,
    private val sharedPreferences: SharedPreferences,
) {
    private val themesDir by lazy {
        File(context.getDir(MODE_FILES), Const.THEME_INSTALL_DIRECTORY).apply {
            if (!exists()) mkdirs()
        }
    }

    private val gson by lazy { Gson() }

    /**
     * Load theme profiles file.
     * Invalid theme profiles will not be displayed.
     */
    fun loadThemesList(): List<ThemeBean> {
        val themeBeans = ArrayList<ThemeBean>()
        val listFiles = themesDir.listFiles()
        listFiles?.sortedBy { it.lastModified() }?.forEach { file ->
            themeBeans.add(
                FileInputStream(file).use {
                    BufferedInputStream(it).use { buffered ->
                        gson.fromJson(
                            String(buffered.readBytes(), Charsets.UTF_8),
                            ThemeBean::class.java
                        )
                    }
                }.copy(label = file.name)
            )
        }
        return themeBeans
    }

    /**
     * Install the custom theme profile on the hard disk.
     * Users can export the "material-theme.json" file from
     * the "Material Theme Builder" website (https://m3.material.io/theme-builder).
     *
     * @param themeFile Theme profile file.
     *
     * @throws RuntimeException When a theme with the same name as the custom theme file
     * already exists in the installation directory, a [RuntimeException] exception will be thrown.
     */
    suspend fun installTheme(themeFile: File): Unit = withContext(Dispatchers.IO) {
        val themeFileName = themeFile.name
        val installFile = File(themesDir, themeFileName)
        if (installFile.exists()) throw RuntimeException("The theme profile with the name \"$themeFileName\" already exists in the theme installation directory.")
        FileInputStream(themeFile).use {
            BufferedInputStream(it).use { bufferedInputStream ->
                FileOutputStream(installFile).use { out ->
                    BufferedOutputStream(out).use { bufferedOutputStream ->
                        bufferedInputStream.copyTo(bufferedOutputStream)
                    }
                }
            }
        }
    }

    /**
     * Tells the application that the [themeBean] has been used (but will not be loaded into memory).
     */
    suspend fun useTheme(themeBean: ThemeBean, onCreate: (LintThemeColorSchemes) -> Unit): Unit =
        withContext(Dispatchers.IO) {
            sharedPreferences.putString(Const.USED_THEME_FILE_NAME, themeBean.label)
            onCreate(
                LintThemeColorSchemes(
                    label = themeBean.label,
                    light = this@LintTheme lightColorScheme themeBean.schemes.light,
                    dark = this@LintTheme lightColorScheme themeBean.schemes.dark,
                )
            )
        }

    /**
     * Load the theme that has been used (provided that it has been installed).
     */
    fun getUsedTheme(): LintThemeColorSchemes {
        val themeName = sharedPreferences.getString(Const.USED_THEME_FILE_NAME)
        val theme = read(themeName ?: "")
        val schemes = theme.schemes
        return LintThemeColorSchemes(
            label = theme.label,
            light = this lightColorScheme schemes.light,
            dark = this darkColorScheme schemes.dark,
        )
    }

    fun read(themeName: String): ThemeBean =
        gson.fromJson(File(themesDir, themeName).readText(), ThemeBean::class.java).copy(label = themeName)

    data class LintThemeColorSchemes(
        val label: String? = null,
        val light: ColorScheme,
        val dark: ColorScheme,
    )

    fun uninstallThemeByName(name: String, onFailed: () -> Unit = {}, onSuccess: () -> Unit) {
        val usedThemeName = sharedPreferences.getString(Const.USED_THEME_FILE_NAME)
        if (name == usedThemeName)
            throw RuntimeException("The current theme is in use and cannot be uninstalled!")
        val file = File(themesDir, name)
        if (file.delete()) {
            onSuccess()
        }else {
            onFailed()
        }
    }

    infix fun lightColorScheme(scheme: SchemesBasic): ColorScheme = lightColorScheme(
        primary = scheme.primary.toColor(),
        onPrimary = scheme.onPrimary.toColor(),
        primaryContainer = scheme.primaryContainer.toColor(),
        onPrimaryContainer = scheme.onPrimaryContainer.toColor(),
        inversePrimary = scheme.inversePrimary.toColor(),
        secondary = scheme.secondary.toColor(),
        onSecondary = scheme.onSecondary.toColor(),
        secondaryContainer = scheme.secondaryContainer.toColor(),
        onSecondaryContainer = scheme.onSecondaryContainer.toColor(),
        tertiary = scheme.tertiary.toColor(),
        onTertiary = scheme.onTertiary.toColor(),
        tertiaryContainer = scheme.tertiaryContainer.toColor(),
        onTertiaryContainer = scheme.onTertiaryContainer.toColor(),
        background = scheme.background.toColor(),
        onBackground = scheme.onBackground.toColor(),
        surface = scheme.surface.toColor(),
        onSurface = scheme.onSurface.toColor(),
        surfaceVariant = scheme.surfaceVariant.toColor(),
        onSurfaceVariant = scheme.onSurfaceVariant.toColor(),
        surfaceTint = scheme.surfaceTint.toColor(),
        inverseSurface = scheme.inverseSurface.toColor(),
        inverseOnSurface = scheme.inverseOnSurface.toColor(),
        error = scheme.error.toColor(),
        onError = scheme.onError.toColor(),
        errorContainer = scheme.errorContainer.toColor(),
        onErrorContainer = scheme.onErrorContainer.toColor(),
        outline = scheme.outline.toColor(),
        outlineVariant = scheme.outlineVariant.toColor(),
        scrim = scheme.scrim.toColor(),
        surfaceBright = scheme.surfaceBright.toColor(),
        surfaceContainer = scheme.surfaceContainer.toColor(),
        surfaceContainerHigh = scheme.surfaceContainerHigh.toColor(),
        surfaceContainerHighest = scheme.surfaceContainerHighest.toColor(),
        surfaceContainerLow = scheme.surfaceContainerLow.toColor(),
        surfaceContainerLowest = scheme.surfaceContainerLowest.toColor(),
        surfaceDim = scheme.surfaceDim.toColor(),
    )

    infix fun darkColorScheme(scheme: SchemesBasic): ColorScheme = darkColorScheme(
        primary = scheme.primary.toColor(),
        onPrimary = scheme.onPrimary.toColor(),
        primaryContainer = scheme.primaryContainer.toColor(),
        onPrimaryContainer = scheme.onPrimaryContainer.toColor(),
        inversePrimary = scheme.inversePrimary.toColor(),
        secondary = scheme.secondary.toColor(),
        onSecondary = scheme.onSecondary.toColor(),
        secondaryContainer = scheme.secondaryContainer.toColor(),
        onSecondaryContainer = scheme.onSecondaryContainer.toColor(),
        tertiary = scheme.tertiary.toColor(),
        onTertiary = scheme.onTertiary.toColor(),
        tertiaryContainer = scheme.tertiaryContainer.toColor(),
        onTertiaryContainer = scheme.onTertiaryContainer.toColor(),
        background = scheme.background.toColor(),
        onBackground = scheme.onBackground.toColor(),
        surface = scheme.surface.toColor(),
        onSurface = scheme.onSurface.toColor(),
        surfaceVariant = scheme.surfaceVariant.toColor(),
        onSurfaceVariant = scheme.onSurfaceVariant.toColor(),
        surfaceTint = scheme.surfaceTint.toColor(),
        inverseSurface = scheme.inverseSurface.toColor(),
        inverseOnSurface = scheme.inverseOnSurface.toColor(),
        error = scheme.error.toColor(),
        onError = scheme.onError.toColor(),
        errorContainer = scheme.errorContainer.toColor(),
        onErrorContainer = scheme.onErrorContainer.toColor(),
        outline = scheme.outline.toColor(),
        outlineVariant = scheme.outlineVariant.toColor(),
        scrim = scheme.scrim.toColor(),
        surfaceBright = scheme.surfaceBright.toColor(),
        surfaceContainer = scheme.surfaceContainer.toColor(),
        surfaceContainerHigh = scheme.surfaceContainerHigh.toColor(),
        surfaceContainerHighest = scheme.surfaceContainerHighest.toColor(),
        surfaceContainerLow = scheme.surfaceContainerLow.toColor(),
        surfaceContainerLowest = scheme.surfaceContainerLowest.toColor(),
        surfaceDim = scheme.surfaceDim.toColor(),
    )
}