package io.github.lumkit.desktop.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Theme file export entity.
 */
data class ThemeBean(
    @SerializedName("label") val label: String?,
    @SerializedName("seed") val seed: String,
    @SerializedName("schemes") val schemes: Schemes
) : Serializable

data class Schemes(
    @SerializedName("light") val light: SchemesBasic,
    @SerializedName("dark") val dark: SchemesBasic,
): Serializable

data class SchemesBasic(
    @SerializedName("primary") val primary: String,
    @SerializedName("surfaceTint") val surfaceTint: String,
    @SerializedName("onPrimary") val onPrimary: String,
    @SerializedName("primaryContainer") val primaryContainer: String,
    @SerializedName("onPrimaryContainer") val onPrimaryContainer: String,
    @SerializedName("secondary") val secondary: String,
    @SerializedName("onSecondary") val onSecondary: String,
    @SerializedName("secondaryContainer") val secondaryContainer: String,
    @SerializedName("onSecondaryContainer") val onSecondaryContainer: String,
    @SerializedName("tertiary") val tertiary: String,
    @SerializedName("onTertiary") val onTertiary: String,
    @SerializedName("tertiaryContainer") val tertiaryContainer: String,
    @SerializedName("onTertiaryContainer") val onTertiaryContainer: String,
    @SerializedName("error") val error: String,
    @SerializedName("onError") val onError: String,
    @SerializedName("errorContainer") val errorContainer: String,
    @SerializedName("onErrorContainer") val onErrorContainer: String,
    @SerializedName("background") val background: String,
    @SerializedName("onBackground") val onBackground: String,
    @SerializedName("surface") val surface: String,
    @SerializedName("onSurface") val onSurface: String,
    @SerializedName("surfaceVariant") val surfaceVariant: String,
    @SerializedName("onSurfaceVariant") val onSurfaceVariant: String,
    @SerializedName("outline") val outline: String,
    @SerializedName("outlineVariant") val outlineVariant: String,
    @SerializedName("shadow") val shadow: String,
    @SerializedName("scrim") val scrim: String,
    @SerializedName("inverseSurface") val inverseSurface: String,
    @SerializedName("inverseOnSurface") val inverseOnSurface: String,
    @SerializedName("inversePrimary") val inversePrimary: String,
    @SerializedName("primaryFixed") val primaryFixed: String,
    @SerializedName("onPrimaryFixed") val onPrimaryFixed: String,
    @SerializedName("primaryFixedDim") val primaryFixedDim: String,
    @SerializedName("onPrimaryFixedVariant") val onPrimaryFixedVariant: String,
    @SerializedName("secondaryFixed") val secondaryFixed: String,
    @SerializedName("onSecondaryFixed") val onSecondaryFixed: String,
    @SerializedName("secondaryFixedDim") val secondaryFixedDim: String,
    @SerializedName("onSecondaryFixedVariant") val onSecondaryFixedVariant: String,
    @SerializedName("tertiaryFixed") val tertiaryFixed: String,
    @SerializedName("onTertiaryFixed") val onTertiaryFixed: String,
    @SerializedName("tertiaryFixedDim") val tertiaryFixedDim: String,
    @SerializedName("onTertiaryFixedVariant") val onTertiaryFixedVariant: String,
    @SerializedName("surfaceDim") val surfaceDim: String,
    @SerializedName("surfaceBright") val surfaceBright: String,
    @SerializedName("surfaceContainerLowest") val surfaceContainerLowest: String,
    @SerializedName("surfaceContainerLow") val surfaceContainerLow: String,
    @SerializedName("surfaceContainer") val surfaceContainer: String,
    @SerializedName("surfaceContainerHigh") val surfaceContainerHigh: String,
    @SerializedName("surfaceContainerHighest") val surfaceContainerHighest: String,
): Serializable