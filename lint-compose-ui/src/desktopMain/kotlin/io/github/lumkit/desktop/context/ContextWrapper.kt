package io.github.lumkit.desktop.context

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.awt.ComposeWindow

val LocalContextWrapper = compositionLocalOf<ContextWrapper> { error("context_wrapper not initialized") }

abstract class ContextWrapper internal constructor(): Context() {

    internal val toastQueues = mutableStateListOf<ToastQueue>()
    abstract fun getWindow(): ComposeWindow

}