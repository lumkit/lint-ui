package io.github.lumkit.desktop.ui.components

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A horizontal, rounded, draggable progress bar.
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LintHorizontalLinearProgress(
    modifier: Modifier,
    progress: Float,
    onProgressChanged: (Float) -> Unit = {},
    thickness: Dp = 16.dp,
    radius: CornerRadius = CornerRadius(
        with(LocalDensity.current) { (thickness / 2f).toPx() },
        with(LocalDensity.current) { (thickness / 2f).toPx() }
    ),
    easing: Easing = FastOutSlowInEasing,
    color: Color = ProgressIndicatorDefaults.linearColor,
    trackColor: Color = MaterialTheme.colorScheme.surface,
) {
    val density = LocalDensity.current
    var viewWidth = 0f
    val circleRadius = with(density) { thickness.toPx() * .5f }

    var release by remember { mutableStateOf(true) }

    val realProgress = if (!release) {
        progress
    } else {
        val animateProgress by animateFloatAsState(
            progress,
            animationSpec = tween(easing = easing)
        )
        animateProgress
    }

    val thumbSize by animateFloatAsState(
        targetValue = if (release) with(density) { thickness.toPx() * .6f }
        else with(density) { thickness.toPx() * .8f },
    )

    Canvas(
        modifier = modifier.height(thickness)
            .onPointerEvent(PointerEventType.Release) {
                release = true
            }.onPointerEvent(PointerEventType.Press) {
                release = false
                onProgressChanged(((it.changes.first().position.x - circleRadius) / viewWidth).bounds(0f, 1f))
            }.onPointerEvent(PointerEventType.Move) {
                if (!release) {
                    onProgressChanged(((it.changes.first().position.x - circleRadius) / viewWidth).bounds(0f, 1f))
                }
            },
    ) {
        val thicknessPx = thickness.toPx()
        val width = size.width - thicknessPx
        viewWidth = width
        val offsetX = ((width * realProgress) + thicknessPx).bounds(thicknessPx, size.width)
        drawRoundRect(
            color = trackColor,
            size = size,
            cornerRadius = radius
        )
        drawRoundRect(
            color = color,
            size = size.copy(width = offsetX),
            cornerRadius = radius
        )
        val padding = (size.height - thumbSize) * .5f
        drawRoundRect(
            topLeft = Offset(offsetX - thumbSize - padding, padding),
            color = trackColor,
            size = Size(thumbSize, thumbSize),
            cornerRadius = radius
        )
    }
}

@Composable
fun LintHorizontalLinearIndicator(
    modifier: Modifier,
    progress: Float,
    thickness: Dp = 16.dp,
    radius: CornerRadius = CornerRadius(
        with(LocalDensity.current) { (thickness / 2f).toPx() },
        with(LocalDensity.current) { (thickness / 2f).toPx() }
    ),
    easing: Easing = FastOutSlowInEasing,
    color: Color = ProgressIndicatorDefaults.linearColor,
    trackColor: Color = MaterialTheme.colorScheme.surface,
) {
    val realProgress by animateFloatAsState(
        progress,
        animationSpec = tween(easing = easing)
    )
    Canvas(
        modifier = modifier.height(thickness)
    ) {
        val thicknessPx = thickness.toPx()
        val width = size.width - thicknessPx
        val offsetX = ((width * realProgress) + thicknessPx).bounds(thicknessPx, size.width)
        drawRoundRect(
            color = trackColor,
            size = size,
            cornerRadius = radius
        )
        drawRoundRect(
            color = color,
            size = size.copy(width = offsetX),
            cornerRadius = radius
        )
    }
}

@Composable
fun LintCircleIndicator(
    modifier: Modifier,
    progress: Float,
    thickness: Dp = 16.dp,
    easing: Easing = FastOutSlowInEasing,
    color: Color = ProgressIndicatorDefaults.linearColor,
    trackColor: Color = MaterialTheme.colorScheme.surface,
) {
    val realProgress by animateFloatAsState(
        progress,
        animationSpec = tween(easing = easing)
    )
    Canvas(
        modifier = modifier
    ) {
        val thicknessPx = thickness.toPx()
        drawCircle(
            color = trackColor,
            style = Stroke(width = thicknessPx)
        )
        drawArc(
            color = color,
            startAngle = -90f,
            sweepAngle = 360 * realProgress,
            useCenter = false,
            style = Stroke(width = thicknessPx, cap = StrokeCap.Round)
        )
    }
}

@Composable
fun LintCircleIndicator(
    modifier: Modifier = Modifier,
    color: Color = ProgressIndicatorDefaults.circularColor,
    strokeWidth: Dp = ProgressIndicatorDefaults.CircularStrokeWidth,
    trackColor: Color = ProgressIndicatorDefaults.circularTrackColor,
    strokeCap: StrokeCap = StrokeCap.Round,
) {
    CircularProgressIndicator(
        modifier, color, strokeWidth, trackColor, strokeCap
    )
}

@Composable
fun LintHorizontalLinearIndicator(
    modifier: Modifier = Modifier,
    color: Color = ProgressIndicatorDefaults.linearColor,
    trackColor: Color = ProgressIndicatorDefaults.linearTrackColor,
    strokeCap: StrokeCap = StrokeCap.Round,
) {
    LinearProgressIndicator(
        modifier, color, trackColor, strokeCap
    )
}

internal infix fun Float.min(min: Float) = if (this <= min) min else this
internal infix fun Float.max(max: Float) = if (this >= max) max else this
internal fun Float.bounds(min: Float, max: Float) = if (this >= max) max else if (this <= min) min else this