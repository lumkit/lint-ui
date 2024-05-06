package io.github.lumkit.desktop.example.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.lumkit.desktop.ui.components.LintButton
import io.github.lumkit.desktop.ui.dialog.LintAlert

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AlertExampleScreen() {
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
                Text("无按钮弹窗")
            }

            LintAlert(
                visible = isShow,
                title = "提示",
            ) {
                Text("这是一个基本的无按钮弹窗")
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            val isShow = remember { mutableStateOf(false) }
            LintButton(
                onClick = {
                    isShow.value = true
                }
            ) {
                Text("单按钮弹窗")
            }

            LintAlert(
                visible = isShow,
                title = "提示",
                confirmButtonText = "确定",
                onConfirm = {
                    isShow.value = false
                }
            ) {
                Text("这是一个基本的单按钮弹窗")
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            val isShow = remember { mutableStateOf(false) }
            LintButton(
                onClick = {
                    isShow.value = true
                }
            ) {
                Text("双按钮弹窗")
            }

            LintAlert(
                visible = isShow,
                title = "提示",
                confirmButtonText = "确定",
                onConfirm = {
                    isShow.value = false
                },
                cancelButtonText = "取消",
                onCancel = {
                    isShow.value = false
                }
            ) {
                Text("这是一个基本的双按钮弹窗")
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            val isShow = remember { mutableStateOf(false) }
            LintButton(
                onClick = {
                    isShow.value = true
                }
            ) {
                Text("复合布局弹窗")
            }

            LintAlert(
                visible = isShow,
                title = "提示",
                confirmButtonText = "确定",
                onConfirm = {
                    isShow.value = false
                },
                cancelButtonText = "取消",
                onCancel = {
                    isShow.value = false
                },
                scrollable = false
            ) {
                Text("这是一个复合布局弹窗（偷个懒）")
                ProgressExampleScreen()
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            val isShow = remember { mutableStateOf(false) }
            LintButton(
                onClick = {
                    isShow.value = true
                }
            ) {
                Text("不可取消双按钮弹窗")
            }

            LintAlert(
                visible = isShow,
                isCancel = false,
                title = "提示",
                confirmButtonText = "确定",
                onConfirm = {
                    isShow.value = false
                },
                cancelButtonText = "取消",
                onCancel = {
                    isShow.value = false
                }
            ) {
                Text("这是一个不可取消双按钮弹窗")
            }
        }
    }
}