package io.github.lumkit.desktop.data

import kotlinx.serialization.Serializable

@Serializable
data class WindowSize(
    val width: Float,
    val height: Float,
)
