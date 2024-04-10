package io.github.lumkit.desktop.example.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.lumkit.desktop.ui.components.LintCircleIndicator
import io.github.lumkit.desktop.ui.components.LintHorizontalLinearIndicator
import io.github.lumkit.desktop.ui.components.LintHorizontalLinearProgress

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProgressExampleScreen() {
    var progress by remember { mutableStateOf(.5f) }

    FlowRow(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(28.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text("水平线性拖动条（${String.format("%.2f%s", progress * 100f, "%")}）")
            LintHorizontalLinearProgress(
                modifier = Modifier.fillMaxWidth(),
                progress = progress,
                onProgressChanged = {
                    progress = it
                }
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text("水平线性进度条（${String.format("%.2f%s", progress * 100f, "%")}）")
            LintHorizontalLinearIndicator(
                modifier = Modifier.fillMaxWidth(),
                progress = progress
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text("圆形进度条（${String.format("%.2f%s", progress * 100f, "%")}）")
            LintCircleIndicator(
                modifier = Modifier.size(65.dp),
                thickness = 8.dp,
                progress = progress,
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text("水平线性加载条")
            LintHorizontalLinearIndicator(
                modifier = Modifier.fillMaxWidth().height(8.dp),
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text("圆形加载条")
            LintCircleIndicator(
                modifier = Modifier.size(65.dp),
                strokeWidth = 8.dp
            )
        }
    }
}
