package io.github.lumkit.desktop.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import io.github.lumkit.desktop.ui.components.LintButton
import io.github.lumkit.desktop.ui.components.LintHorizontalDivider

@Composable
fun LintAlert(
    visible: MutableState<Boolean>,
    isCancel: Boolean = true,
    title: String,
    cancelButtonText: String? = null,
    onCancel: (() -> Unit)? = null,
    confirmButtonText: String? = null,
    onConfirm: (() -> Unit)? = null,
    scrollable: Boolean = true,
    content: @Composable ColumnScope.() -> Unit
) {
    DialogBasic(
        visible, isCancel
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(Modifier.background(MaterialTheme.colorScheme.secondaryContainer.copy(alpha = .3f))) {
                Column(
                    Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .padding(horizontal = 28.dp)
                        .fillMaxWidth()
                        .weight(1f, fill = false)
                ) {
                    Spacer(Modifier.height(28.dp))
                    Text(
                        style = MaterialTheme.typography.titleLarge,
                        text = title,
                    )
                    Spacer(Modifier.height(12.dp))
                    Column(
                        modifier =  Modifier
                            .fillMaxWidth()
                            .then(
                                if (scrollable) Modifier.verticalScroll(rememberScrollState())
                                else Modifier
                            )
                    ) {
                        ProvideTextStyle(MaterialTheme.typography.bodyMedium) {
                            content()
                        }
                    }
                    Spacer(Modifier.height(28.dp))
                }
                // Button Grid
                if (!(onConfirm == null && onCancel == null)) {
                    // Divider
                    LintHorizontalDivider()
                    Box(
                        Modifier
                            .height(80.dp)
                            .padding(horizontal = 28.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            if (onConfirm != null) {
                                LintButton(modifier = Modifier.weight(1f), onClick = onConfirm) {
                                    Text(confirmButtonText ?: "")
                                }
                            }
                            if (onCancel != null) {
                                LintButton(
                                    modifier = Modifier.weight(1f),
                                    onClick = onCancel,
                                    colors = ButtonDefaults.filledTonalButtonColors()
                                ) {
                                    Text(cancelButtonText ?: "")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DialogBasic(
    visible: MutableState<Boolean>,
    isCancel: Boolean = true,
    content: @Composable () -> Unit
) {
    if (visible.value) {
        Dialog(
            onDismissRequest = {
                if (isCancel) {
                    visible.value = false
                }
            },
            properties = DialogProperties(
                dismissOnBackPress = isCancel,
                dismissOnClickOutside = isCancel,
            )
        ) {
            Box(
                modifier = Modifier.padding(28.dp)
            ) {
                content()
            }
        }
    }
}