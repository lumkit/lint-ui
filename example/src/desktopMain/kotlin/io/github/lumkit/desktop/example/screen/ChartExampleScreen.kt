package io.github.lumkit.desktop.example.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.lumkit.desktop.ui.components.LintStackChart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import kotlin.random.Random

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChartExampleScreen() {
    FlowRow(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(28.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            var progress by remember { mutableStateOf(0f) }
            Text(text = "栈条形图")
            LintStackChart(
                modifier = Modifier.fillMaxWidth()
                    .height(72.dp),
                progress = progress
            )

            LaunchedEffect(Unit) {
                withContext(Dispatchers.IO) {
                    while (isActive) {
                        progress = Random.nextFloat()
                        delay(1000)
                    }
                }
            }
        }
    }
}