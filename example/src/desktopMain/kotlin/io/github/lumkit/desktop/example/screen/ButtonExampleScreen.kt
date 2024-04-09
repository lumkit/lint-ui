package io.github.lumkit.desktop.example.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import io.github.lumkit.desktop.ui.components.*
import lint_ui.example.generated.resources.Res
import lint_ui.example.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class, ExperimentalLayoutApi::class)
@Composable
fun ButtonExampleScreen() {
    FlowRow(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            var count by remember { mutableStateOf(0) }
            Text("主题色按钮")
            LintButton(
                onClick = {
                    count++
                }
            ) {
                Text("点我！（$count 次了）")
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            var count by remember { mutableStateOf(0) }
            Text("文本按钮")
            LintTextButton(
                onClick = {
                    count++
                }
            ) {
                Text("点我！（$count 次了）")
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            var isShow by remember { mutableStateOf(false) }
            Text("描边按钮")
            LintOutlinedButton(
                onClick = {
                    isShow = !isShow
                }
            ) {
                Text("点我一下")
            }

            AnimatedVisibility(isShow) {
                Image(
                    modifier = Modifier.size(120.dp),
                    painter = painterResource(Res.drawable.compose_multiplatform),
                    contentDescription = null
                )
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            var isShow by remember { mutableStateOf(false) }
            Text("阴影按钮")
            LintElevatedButton(
                onClick = {
                    isShow = !isShow
                }
            ) {
                Text("点我一下")
            }

            val infiniteTransition = rememberInfiniteTransition()
            val rotation by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 2000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            )
            AnimatedVisibility(isShow) {
                Image(
                    modifier = Modifier.size(120.dp).rotate(rotation),
                    painter = painterResource(Res.drawable.compose_multiplatform),
                    contentDescription = null
                )
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            var isShow by remember { mutableStateOf(false) }
            Text("填充色调按钮")
            LintFilledTonalButton(
                onClick = {
                    isShow = !isShow
                }
            ) {
                Text("点我一下")
            }

            val infiniteTransition = rememberInfiniteTransition()
            val alpha by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 2000, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )
            AnimatedVisibility(isShow) {
                Image(
                    modifier = Modifier.size(120.dp).alpha(alpha),
                    painter = painterResource(Res.drawable.compose_multiplatform),
                    contentDescription = null
                )
            }
        }
    }
}