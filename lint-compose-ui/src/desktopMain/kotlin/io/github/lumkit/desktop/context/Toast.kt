package io.github.lumkit.desktop.context

import androidx.compose.ui.Alignment

object Toast {

    const val LENGTH_SHORT: Long = 1500
    const val LENGTH_LONG: Long = 3000

    fun ContextWrapper.showToast(message: String, time: Long, alignment: Alignment = Alignment.BottomEnd) {
        toastQueues.add(ToastQueue(message, time, alignment))
    }

}