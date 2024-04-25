package io.github.lumkit.desktop.example.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Android
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.lumkit.desktop.context.LocalContextWrapper
import io.github.lumkit.desktop.context.Toast
import io.github.lumkit.desktop.context.Toast.showToast
import io.github.lumkit.desktop.ui.components.*
import io.github.lumkit.desktop.ui.theme.AnimatedLintTheme
import io.github.lumkit.desktop.ui.theme.LintTheme
import lint_ui.example.generated.resources.Res
import lint_ui.example.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalLayoutApi::class, ExperimentalResourceApi::class)
@Composable
fun ToastExampleScreen() {
    FlowRow(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            val context = LocalContextWrapper.current
            var message by remember { mutableStateOf("This is a toast!") }
            var time by remember { mutableStateOf(Toast.LENGTH_SHORT) }
            var alignment by remember { mutableStateOf(Alignment.BottomEnd) }

            LintTextField(
                value = message,
                onValueChange = { message = it },
                label = {
                    Text("消息内容")
                },
            )
            Spacer(modifier = Modifier)
            Text("显示时长")
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Row(
                    modifier = Modifier.clickable { time = Toast.LENGTH_SHORT },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Checkbox(checked = time == Toast.LENGTH_SHORT, onCheckedChange = null)
                    Text("短时间")
                }
                Row(
                    modifier = Modifier.clickable { time = Toast.LENGTH_LONG },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Checkbox(checked = time == Toast.LENGTH_LONG, onCheckedChange = null)
                    Text("较长时间")
                }
            }
            Spacer(modifier = Modifier)
            Text("位置")
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.clickable { alignment = Alignment.TopStart },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Checkbox(checked = alignment == Alignment.TopStart, onCheckedChange = null)
                    Text("TopStart")
                }
                Row(
                    modifier = Modifier.clickable { alignment = Alignment.TopCenter },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Checkbox(checked = alignment == Alignment.TopCenter, onCheckedChange = null)
                    Text("TopCenter")
                }
                Row(
                    modifier = Modifier.clickable { alignment = Alignment.TopEnd },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Checkbox(checked = alignment == Alignment.TopEnd, onCheckedChange = null)
                    Text("TopEnd")
                }
                Row(
                    modifier = Modifier.clickable { alignment = Alignment.CenterStart },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Checkbox(checked = alignment == Alignment.CenterStart, onCheckedChange = null)
                    Text("CenterStart")
                }
                Row(
                    modifier = Modifier.clickable { alignment = Alignment.Center },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Checkbox(checked = alignment == Alignment.Center, onCheckedChange = null)
                    Text("Center")
                }
                Row(
                    modifier = Modifier.clickable { alignment = Alignment.CenterEnd },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Checkbox(checked = alignment == Alignment.CenterEnd, onCheckedChange = null)
                    Text("CenterEnd")
                }
                Row(
                    modifier = Modifier.clickable { alignment = Alignment.BottomStart },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Checkbox(checked = alignment == Alignment.BottomStart, onCheckedChange = null)
                    Text("BottomStart")
                }
                Row(
                    modifier = Modifier.clickable { alignment = Alignment.BottomCenter },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Checkbox(checked = alignment == Alignment.BottomCenter, onCheckedChange = null)
                    Text("BottomCenter")
                }
                Row(
                    modifier = Modifier.clickable { alignment = Alignment.BottomEnd },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Checkbox(checked = alignment == Alignment.BottomEnd, onCheckedChange = null)
                    Text("BottomEnd")
                }
            }
            Spacer(modifier = Modifier)
            LintButton(
                onClick = {
                    // Show your toast
                    context.showToast(
                        message,
                        time,
                        alignment,
                    )
                }
            ) {
                Text("弹出默认提示")
            }
            LintButton(
                onClick = {
                    // Show your toast
                    context.showToast(
                        message,
                        time,
                        alignment,
                        leadingIcon = {
                            Image(painterResource(Res.drawable.compose_multiplatform), null)
                        },
                        trailingIcon = {
                            Icon(Icons.Default.Check, contentDescription = null, tint = Color.Green)
                        }
                    )
                }
            ) {
                Text("弹出有图标的提示")
            }
            LintButton(
                onClick = {
                    // Show your toast
                    context.showToast(
                        time,
                        alignment,
                    ) {
                        // custom toast content
                        LintTheme {
                            Surface(
                                modifier = Modifier.padding(16.dp).clip(CircleShape)
                                    .shadow(5.dp),
                                color = Color(0xFF85C077),
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    Text(message, color = Color.White)
                                    Icon(Icons.Default.Check, contentDescription = null, tint = Color.White)
                                }
                            }
                        }
                    }
                }
            ) {
                Text("弹出自定义内容提示")
            }
        }
    }
}