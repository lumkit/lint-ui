package io.github.lumkit.desktop.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import com.google.gson.Gson
import io.github.lumkit.desktop.Const
import io.github.lumkit.desktop.data.WindowSize
import io.github.lumkit.desktop.preferences.LocalSharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * A window with full coverage.
 * @see [Window]
 */
@Composable
fun LintWindow(
    onCloseRequest: () -> Unit,
    state: WindowState = rememberWindowState(
        position = WindowPosition(Alignment.Center)
    ),
    visible: Boolean = true,
    title: String = "Lint UI",
    icon: Painter? = null,
    undecorated: Boolean = false,
    transparent: Boolean = false,
    resizable: Boolean = true,
    enabled: Boolean = true,
    focusable: Boolean = true,
    alwaysOnTop: Boolean = false,
    onPreviewKeyEvent: (KeyEvent) -> Boolean = { false },
    onKeyEvent: (KeyEvent) -> Boolean = { false },
    content: @Composable FrameWindowScope.() -> Unit
) {
    Window(
        onCloseRequest,
        state,
        visible,
        title,
        icon,
        undecorated,
        transparent,
        resizable,
        enabled,
        focusable,
        alwaysOnTop,
        onPreviewKeyEvent,
        onKeyEvent
    ) {
        if (!transparent) {
            // make ComposeWindowPanel cover the whole window.
            MenuBar {}
        }
        content()
    }
}

/**
 * Remember the last size window.
 * @see [LintWindow]
 */
@Composable
fun LintRememberWindow(
    onCloseRequest: () -> Unit,
    state: WindowState = rememberWindowState(
        position = WindowPosition(Alignment.Center)
    ),
    visible: Boolean = true,
    title: String = "Lint UI",
    icon: Painter? = null,
    undecorated: Boolean = false,
    transparent: Boolean = false,
    resizable: Boolean = true,
    enabled: Boolean = true,
    focusable: Boolean = true,
    alwaysOnTop: Boolean = false,
    onPreviewKeyEvent: (KeyEvent) -> Boolean = { false },
    onKeyEvent: (KeyEvent) -> Boolean = { false },
    content: @Composable FrameWindowScope.() -> Unit
) {
    val sharedPreferences = LocalSharedPreferences.current
    val gson = remember { Gson() }

    try {
        val windowSize = gson.fromJson(sharedPreferences.getString(Const.WINDOW_SIZE), WindowSize::class.java)
        state.size = DpSize(windowSize.width.dp, windowSize.height.dp)
    } catch (_: Exception) { }

    LaunchedEffect(Unit) {
        snapshotFlow { state.size }
            .filter { state.placement == WindowPlacement.Floating }
            .onEach {
                try {
                    val size = WindowSize(it.width.value, it.height.value)
                    sharedPreferences.putString(Const.WINDOW_SIZE, gson.toJson(size))
                }catch (_ : Exception){}
            }.flowOn(Dispatchers.IO)
            .launchIn(this)
    }

    LintWindow(
        onCloseRequest,
        state,
        visible,
        title,
        icon,
        undecorated,
        transparent,
        resizable,
        enabled,
        focusable,
        alwaysOnTop,
        onPreviewKeyEvent,
        onKeyEvent,
        content
    )
}