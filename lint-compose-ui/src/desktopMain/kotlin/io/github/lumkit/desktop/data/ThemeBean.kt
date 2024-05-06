package io.github.lumkit.desktop.data

import kotlinx.serialization.Serializable

/**
 * Theme file export entity.
 */
@Serializable
data class ThemeBean(
    val label: String? = null,
    val seed: String,
    val schemes: Schemes
)

@Serializable
data class Schemes(
    val light: SchemesBasic,
    val dark: SchemesBasic,
)

@Serializable
data class SchemesBasic(
    val primary: String,
    val surfaceTint: String,
    val onPrimary: String,
    val primaryContainer: String,
    val onPrimaryContainer: String,
    val secondary: String,
    val onSecondary: String,
    val secondaryContainer: String,
    val onSecondaryContainer: String,
    val tertiary: String,
    val onTertiary: String,
    val tertiaryContainer: String,
    val onTertiaryContainer: String,
    val error: String,
    val onError: String,
    val errorContainer: String,
    val onErrorContainer: String,
    val background: String,
    val onBackground: String,
    val surface: String,
    val onSurface: String,
    val surfaceVariant: String,
    val onSurfaceVariant: String,
    val outline: String,
    val outlineVariant: String,
    val shadow: String,
    val scrim: String,
    val inverseSurface: String,
    val inverseOnSurface: String,
    val inversePrimary: String,
    val primaryFixed: String,
    val onPrimaryFixed: String,
    val primaryFixedDim: String,
    val onPrimaryFixedVariant: String,
    val secondaryFixed: String,
    val onSecondaryFixed: String,
    val secondaryFixedDim: String,
    val onSecondaryFixedVariant: String,
    val tertiaryFixed: String,
    val onTertiaryFixed: String,
    val tertiaryFixedDim: String,
    val onTertiaryFixedVariant: String,
    val surfaceDim: String,
    val surfaceBright: String,
    val surfaceContainerLowest: String,
    val surfaceContainerLow: String,
    val surfaceContainer: String,
    val surfaceContainerHigh: String,
    val surfaceContainerHighest: String,
)