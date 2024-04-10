package io.github.lumkit.desktop.ui.components

import androidx.compose.foundation.HorizontalScrollbar
import androidx.compose.foundation.LocalScrollbarStyle
import androidx.compose.foundation.ScrollbarStyle
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LintVerticalScrollBar(
    adapter: androidx.compose.foundation.v2.ScrollbarAdapter,
    modifier: Modifier = Modifier,
    reverseLayout: Boolean = false,
    style: ScrollbarStyle = LocalScrollbarStyle.current,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    VerticalScrollbar(
        adapter, modifier.width(4.dp), reverseLayout, style, interactionSource
    )
}

@Composable
fun LintHorizontalScrollbar(
    adapter: androidx.compose.foundation.v2.ScrollbarAdapter,
    modifier: Modifier = Modifier,
    reverseLayout: Boolean = false,
    style: ScrollbarStyle = LocalScrollbarStyle.current,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    HorizontalScrollbar(
        adapter, modifier.height(4.dp), reverseLayout, style, interactionSource
    )
}