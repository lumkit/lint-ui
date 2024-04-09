package io.github.lumkit.desktop.ui.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.formdev.flatlaf.FlatLaf
import com.formdev.flatlaf.themes.FlatMacDarkLaf
import com.formdev.flatlaf.themes.FlatMacLightLaf
import io.github.lumkit.desktop.data.DarkThemeMode
import javax.swing.UIManager

internal val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
)

internal val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
)

@Composable
fun LintTheme(
    content: @Composable () -> Unit
) {
    val themeStore = LocalThemeStore.current

    val colorScheme = when (themeStore.darkTheme) {
        DarkThemeMode.SYSTEM -> {
            if (themeStore.isSystemDarkTheme) {
                themeStore.isDarkTheme = true
                themeStore.colorSchemes.dark
            } else {
                themeStore.isDarkTheme = false
                themeStore.colorSchemes.light
            }
        }

        DarkThemeMode.LIGHT -> {
            themeStore.isDarkTheme = false
            themeStore.colorSchemes.light
        }

        DarkThemeMode.DARK -> {
            themeStore.isDarkTheme = true
            themeStore.colorSchemes.dark
        }
    }

    // update window theme
    try {
        val primary = colorScheme.primary
        FlatLaf.setSystemColorGetter { name: String ->
            if (name == "accent")
                java.awt.Color(primary.toArgb())
            else null
        }
        if (themeStore.isDarkTheme) {
            FlatMacDarkLaf.setup()
            FlatMacDarkLaf.updateUILater()
        } else {
            FlatMacLightLaf.setup()
            FlatMacLightLaf.updateUILater()
        }
    } catch (_: Exception) {}

    val javaColor = UIManager.getColor("Panel.background")

    MaterialTheme(
        colorScheme = colorScheme.copy(
            background = Color(javaColor.red, javaColor.green, javaColor.blue, javaColor.alpha),
        ),
        content = content
    )
}

@Composable
fun AnimatedLintTheme(
    modifier: Modifier,
    content: @Composable () -> Unit
) {
    LintTheme {
        val background by animateColorAsState(
            MaterialTheme.colorScheme.background,
            animationSpec = tween(easing = LinearEasing)
        )
        Surface(
            modifier = modifier,
            color = background,
            content = content
        )
    }
}