package io.github.lumkit.desktop.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.util.concurrent.LinkedBlockingQueue

@Composable
fun LintStackChart(
    modifier: Modifier = Modifier,
    progress: Float,
    chartWith: Dp = 10.dp,
    defaultColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = .125f),
    midColor: Color = Color(0xFFFC8A1B),
    highColor: Color = Color(0xFFF9592F),
) {
    val loadHistory = remember { LinkedBlockingQueue<Float>() }

    Canvas(
        modifier = modifier
    ) {
        val width = this.drawContext.size.width
        val height = this.drawContext.size.height
        val chartWithPx = chartWith.toPx()
        val chartCount = (width / chartWithPx).toInt()

        val space = (width - chartCount * chartWithPx) / 2f

        if (loadHistory.size <= 0) {
            for (i in 0 until chartCount - 1) {
                loadHistory.add(0f)
            }
        }

        loadHistory.put((progress * 100f).bounds(0f, 100f))

        if (loadHistory.size > chartCount) {
            for (i in chartCount until loadHistory.size) {
                loadHistory.poll()
            }
        }

        var index = 0
        loadHistory.forEach { ratio ->
            var chartColor = if (ratio > 85f) {
                highColor
            } else if (ratio > 65f) {
                midColor
            } else {
                defaultColor
            }
            chartColor = if (ratio > 50f) {
                chartColor.copy(alpha = 1f)
            } else {
                val fl = 0.5f + (ratio / 100.0f)
                chartColor.copy(alpha = if (fl <= 0f) 0f else if (fl >= 1f) 1f else fl)
            }
            val top = if (ratio <= 2f) {
                height - 10f
            } else if (ratio >= 98f) {
                0f
            } else {
                (100f - ratio) * height / 100f
            }
            drawRoundRect(
                color = chartColor,
                topLeft = Offset(x = chartWithPx * index + space, y = top),
                size = Size(chartWithPx * .9f, height - top),
                cornerRadius = CornerRadius(3f, 3f)
            )
            index++
        }
    }
}