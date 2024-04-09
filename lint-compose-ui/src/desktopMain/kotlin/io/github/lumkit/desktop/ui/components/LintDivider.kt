package io.github.lumkit.desktop.ui.components

import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun LintVerticalDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = .5.dp,
    color: Color = DividerDefaults.color,
) {
    VerticalDivider(
        modifier, thickness, color
    )
}

@Composable
fun LintHorizontalDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = .5.dp,
    color: Color = DividerDefaults.color,
) {
    HorizontalDivider(
        modifier, thickness, color
    )
}