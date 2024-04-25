package io.github.lumkit.desktop.context

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.lumkit.desktop.ui.theme.AnimatedLintTheme

object Toast {

    const val LENGTH_SHORT: Long = 1500
    const val LENGTH_LONG: Long = 3000

    fun ContextWrapper.showToast(
        time: Long,
        alignment: Alignment = Alignment.BottomEnd,
        content: @Composable () -> Unit
    ) {
        toastQueues.add(
            ToastQueue(time, alignment, content)
        )
    }

    fun ContextWrapper.showToast(
        message: String,
        time: Long = LENGTH_SHORT,
        alignment: Alignment = Alignment.BottomEnd,
        leadingIcon: @Composable (() -> Unit)? = null,
        trailingIcon: @Composable (() -> Unit)? = null,
    ) {
        showToast(
            time,
            alignment,
        ) {
            AnimatedLintTheme(
                modifier = Modifier
                    .padding(16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(
                        border = BorderStroke(1.dp, DividerDefaults.color),
                        shape = RoundedCornerShape(8.dp)
                    ),
            ) {
                ProvideTextStyle(value = MaterialTheme.typography.bodyMedium) {
                    Row(
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        leadingIcon?.let {
                            Surface(
                                modifier = Modifier.size(24.dp),
                                color = Color.Transparent
                            ) {
                                it()
                            }
                        }
                        Text(message)
                        trailingIcon?.let {
                            Surface(
                                modifier = Modifier.size(24.dp),
                                color = Color.Transparent
                            ) {
                                it()
                            }
                        }
                    }
                }
            }
        }
    }

}