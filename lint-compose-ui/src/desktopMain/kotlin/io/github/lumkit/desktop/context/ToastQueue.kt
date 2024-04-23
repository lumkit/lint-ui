package io.github.lumkit.desktop.context

import androidx.compose.ui.Alignment

data class ToastQueue(
    val message: String,
    val time: Long,
    val alignment: Alignment = Alignment.BottomEnd
)
