package io.github.lumkit.desktop.context

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

internal data class ToastQueue(
    val time: Long,
    val alignment: Alignment = Alignment.BottomEnd,
    val content: @Composable () -> Unit,
)
