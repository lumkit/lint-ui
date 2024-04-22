package io.github.lumkit.desktop.example.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import io.github.lumkit.desktop.ui.LintLayerWindow
import io.github.lumkit.desktop.ui.components.LintButton
import lint_ui.example.generated.resources.Res
import lint_ui.example.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import java.awt.Dimension

@OptIn(ExperimentalLayoutApi::class, ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LayerWindowExampleScreen() {
    FlowRow(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            val isShow = remember { mutableStateOf(false) }
            LintButton(
                onClick = {
                    isShow.value = true
                }
            ) {
                Text("启动新窗口")
            }

            val windowState = rememberWindowState(
                position = WindowPosition(Alignment.Center),
            )

            LintLayerWindow(
                showState = isShow,
                state = windowState,
                title = "new window",
                icon = painterResource(Res.drawable.compose_multiplatform),
            ) {
                window.minimumSize = Dimension(400, 300)

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Text("自定义窗口")
                            },
                            navigationIcon = {
                                Image(
                                    modifier = Modifier.padding(16.dp).size(28.dp),
                                    painter = painterResource(Res.drawable.compose_multiplatform),
                                    contentDescription = null
                                )
                            },
                            actions = {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                ) {
                                    IconButton(
                                        onClick = {
                                            windowState.isMinimized = true
                                        }
                                    ) {
                                        Icon(Icons.Filled.Minimize, null)
                                    }
                                    IconButton(
                                        onClick = {
                                            windowState.placement = if (windowState.placement == WindowPlacement.Floating) WindowPlacement.Maximized else WindowPlacement.Floating
                                        }
                                    ) {
                                        Icon(if (windowState.placement == WindowPlacement.Floating) Icons.Default.Maximize else Icons.Default.ContentCopy, null)
                                    }
                                    IconButton(
                                        onClick = {
                                            isShow.value = false
                                        }
                                    ) {
                                        Icon(Icons.Filled.Close, null)
                                    }
                                }
                            }
                        )
                    }
                ) {
                    Column(
                        modifier = Modifier.padding(it).fillMaxSize()
                    ) {
                        ProgressExampleScreen()
                    }
                }
            }
        }
    }
}