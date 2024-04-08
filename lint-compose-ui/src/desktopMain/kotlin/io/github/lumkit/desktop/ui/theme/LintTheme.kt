package io.github.lumkit.desktop.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.compositionLocalOf
import com.google.gson.Gson
import io.github.lumkit.desktop.Const
import io.github.lumkit.desktop.annotates.MODE_FILES
import io.github.lumkit.desktop.common.toColor
import io.github.lumkit.desktop.context.Context
import io.github.lumkit.desktop.data.SchemesBasic
import io.github.lumkit.desktop.data.ThemeBean
import io.github.lumkit.desktop.preferences.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.*

val LocalLintThemeColorSchemes = compositionLocalOf<LintTheme.LintThemeColorSchemes> { error("not provided") }

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
        themesDir.listFiles()?.sortedBy { it.name }?.forEach { file ->
            val name = file.name.also { it.substring(0, it.lastIndexOf(".")) }
            themeBeans.add(
                FileInputStream(file).use {
                    BufferedInputStream(it).use { buffered ->
                        gson.fromJson(
                            String(buffered.readBytes(), Charsets.UTF_8),
                            ThemeBean::class.java
                        )
                    }
                }.copy(label = name)
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
     * Tells the application that the theme profile [themeFile] has been used (but will not be loaded into memory).
     */
    suspend fun useTheme(themeFile: File): Unit = withContext(Dispatchers.IO) {
        val themeBean = read(themeFile)
        sharedPreferences.putString(Const.USED_THEME_FILE_NAME, themeBean.label)
    }

    /**
     * Load the theme that has been used (provided that it has been installed).
     */
    suspend fun getUsedTheme(): LintThemeColorSchemes {
        val themeName = sharedPreferences.getString(Const.USED_THEME_FILE_NAME)
        val theme = read(File(themesDir, themeName ?: ""))
        val schemes = theme.schemes
        return LintThemeColorSchemes(
            light = this lightColorScheme schemes.light,
            dark = this darkColorScheme schemes.dark,
        )
    }

    private suspend fun read(themeFile: File): ThemeBean = withContext(Dispatchers.IO) {
        gson.fromJson(themeFile.readText(), ThemeBean::class.java)
    }

    data class LintThemeColorSchemes(
        val light: ColorScheme,
        val dark: ColorScheme,
    )

    private infix fun lightColorScheme(scheme: SchemesBasic): ColorScheme = lightColorScheme(
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

    private infix fun darkColorScheme(scheme: SchemesBasic): ColorScheme = darkColorScheme(
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