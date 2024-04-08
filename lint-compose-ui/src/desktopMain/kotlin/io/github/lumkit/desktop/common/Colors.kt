package io.github.lumkit.desktop.common

import androidx.compose.ui.graphics.Color

fun String.toColor() : Color {
    val colorString = if (startsWith("#")) substring(1) else this
    val colorLong = colorString.toLong(16)
    return when (colorString.length) {
        // RGB格式，没有透明度，假定为不透明
        6 -> Color((0xFF shl 24) or (colorLong.toInt() and 0xFFFFFF))
        // ARGB格式
        8 -> Color(colorLong)
        else -> throw IllegalArgumentException("Unsupported color format: $this")
    }
}