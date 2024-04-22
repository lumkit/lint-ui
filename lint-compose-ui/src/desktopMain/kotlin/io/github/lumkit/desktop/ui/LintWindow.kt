package io.github.lumkit.desktop.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material3.DividerDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import com.google.gson.Gson
import io.github.lumkit.desktop.Const
import io.github.lumkit.desktop.data.WindowSize
import io.github.lumkit.desktop.preferences.LocalSharedPreferences
import io.github.lumkit.desktop.ui.theme.AnimatedLintTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.awt.Dimension
import java.awt.event.WindowEvent
import java.awt.event.WindowFocusListener

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
        window.minimumSize = Dimension(16, 16)
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
@Deprecated(
    message = "Window size persistence has been encapsulated as a function [WindowState.RememberWindowSize]," +
            " and this component will no longer be recommended.",
)
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
    state.RememberWindowSize(id = title)
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

@Composable
fun LintLayerWindow(
    showState: MutableState<Boolean>,
    onCloseRequest: () -> Unit = { showState.value = false },
    isRememberSize: Boolean = true,
    state: WindowState = rememberWindowState(
        position = WindowPosition(Alignment.Center)
    ),
    visible: Boolean = true,
    title: String,
    icon: Painter? = null,
    resizable: Boolean = true,
    enabled: Boolean = true,
    focusable: Boolean = true,
    alwaysOnTop: Boolean = false,
    shape: RoundedCornerShape = RoundedCornerShape(8.dp),
    borderStroke: BorderStroke = BorderStroke(.8f.dp, DividerDefaults.color),
    onPreviewKeyEvent: (KeyEvent) -> Boolean = { false },
    onKeyEvent: (KeyEvent) -> Boolean = { false },
    content: @Composable FrameWindowScope.() -> Unit
) {
    if (isRememberSize) {
        state.RememberWindowSize(id = title)
    }

    val isShow = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        snapshotFlow { showState.value }
            .onEach {
                if (it) isShow.value = true
            }.launchIn(this)
    }

    LayerWindow(
        showState = showState,
        isShow = isShow,
        onCloseRequest = onCloseRequest,
        state = state,
        visible = visible,
        title = title,
        icon = icon,
        resizable = resizable,
        enabled = enabled,
        focusable = focusable,
        alwaysOnTop = alwaysOnTop,
        shape = shape,
        borderStroke = borderStroke,
        onPreviewKeyEvent = onPreviewKeyEvent,
        onKeyEvent = onKeyEvent,
        content = content
    )
}

@Composable
private fun LayerWindow(
    showState: MutableState<Boolean>,
    isShow: MutableState<Boolean>,
    onCloseRequest: () -> Unit,
    state: WindowState,
    visible: Boolean,
    title: String,
    icon: Painter?,
    resizable: Boolean,
    enabled: Boolean,
    focusable: Boolean,
    alwaysOnTop: Boolean,
    shape: RoundedCornerShape,
    borderStroke: BorderStroke,
    onPreviewKeyEvent: (KeyEvent) -> Boolean,
    onKeyEvent: (KeyEvent) -> Boolean,
    content: @Composable FrameWindowScope.() -> Unit,
) {
    if (isShow.value) {
        LintWindow(
            onCloseRequest = onCloseRequest,
            state = state,
            visible = visible,
            title = title,
            icon = icon,
            undecorated = true,
            transparent = true,
            resizable = resizable,
            enabled = enabled,
            focusable = focusable,
            alwaysOnTop = alwaysOnTop,
            onPreviewKeyEvent = onPreviewKeyEvent,
            onKeyEvent = onKeyEvent,
        ) {
            CardLayer(
                isShow = isShow,
                showState = showState,
                shape = shape,
                borderStroke = borderStroke,
                content = content
            )
        }
    }
}

@Composable
private fun FrameWindowScope.CardLayer(
    isShow: MutableState<Boolean>,
    showState: MutableState<Boolean>,
    shape: RoundedCornerShape,
    borderStroke: BorderStroke,
    content: @Composable FrameWindowScope.() -> Unit
) {
    var isFocused by remember { mutableStateOf(true) }
    val visibleState by animateFloatAsState(
        if (showState.value) 1f else 0f,
        finishedListener = {
            if (it == 0f) {
                isShow.value = false
            }
        }
    )
    val alpha by animateFloatAsState(
        targetValue = if (isFocused) 1f else .75f,
        animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing)
    )
    var initState by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(50)
        initState = true
    }

    window.addWindowFocusListener(object : WindowFocusListener {
        override fun windowGainedFocus(e: WindowEvent?) {
            isFocused = true
        }

        override fun windowLostFocus(e: WindowEvent?) {
            isFocused = false
        }
    })

    WindowDraggableArea(
        modifier = Modifier.fillMaxSize(),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            AnimatedContent(
                modifier = Modifier.fillMaxSize(),
                targetState = visibleState == 1f && initState,
                transitionSpec = {
                    (fadeIn(animationSpec = tween(300, delayMillis = 90)) +
                            scaleIn(initialScale = 0.92f, animationSpec = tween(220, delayMillis = 90)))
                        .togetherWith(fadeOut(animationSpec = tween(220)) + scaleOut(animationSpec = tween(400, delayMillis = 100)))
                }
            ) {
                if (it) {
                    AnimatedLintTheme(
                        modifier = Modifier.fillMaxSize()
                            .clip(shape)
                            .border(borderStroke, shape = shape),
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize().alpha(alpha = alpha),
                        ) {
                            content()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WindowState.RememberWindowSize(
    id: String
) {
    val sharedPreferences = LocalSharedPreferences.current
    val gson = remember { Gson() }
    val key = "${Const.WINDOW_SIZE}-$id"

    try {
        val windowSize = gson.fromJson(sharedPreferences.getString(key), WindowSize::class.java)
        size = DpSize(windowSize.width.dp, windowSize.height.dp)
    } catch (_: Exception) {}

    LaunchedEffect(Unit) {
        snapshotFlow { size }
            .filter { placement == WindowPlacement.Floating }
            .onEach {
                if (it.width.value > 0 && it.height.value > 0) {
                    try {
                        val size = WindowSize(it.width.value, it.height.value)
                        sharedPreferences.putString(key, gson.toJson(size))
                    } catch (_: Exception) {}
                }
            }.flowOn(Dispatchers.IO)
            .launchIn(this)
    }
}