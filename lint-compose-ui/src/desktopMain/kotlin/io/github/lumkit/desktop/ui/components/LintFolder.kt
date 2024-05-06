package io.github.lumkit.desktop.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LintFolder(
    modifier: Modifier,
    expanded: Boolean = true,
    icon: @Composable (() -> Unit)? = null,
    label: @Composable () -> Unit,
    tooltipText: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (RowScope.() -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    children: @Composable ColumnScope.() -> Unit = {}
) {
    LintOutlineCard(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            icon?.also {
                Spacer(Modifier.width(16.dp))
                Surface(
                    modifier = Modifier.size(16.dp),
                    color = Color.Transparent,
                ) {
                    it()
                }
            }
            Spacer(Modifier.width(16.dp))
            Box(
                modifier = Modifier.fillMaxWidth().weight(1f),
                contentAlignment = Alignment.CenterStart
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text("", style = MaterialTheme.typography.titleMedium)
                    Text("", style = MaterialTheme.typography.labelMedium)
                }
                Column {
                    ProvideTextStyle(MaterialTheme.typography.titleMedium) {
                        label()
                    }
                    tooltipText?.also {
                        ProvideTextStyle(MaterialTheme.typography.labelMedium) {
                            it()
                        }
                    }
                }
            }
            trailingIcon?.also {
                Spacer(Modifier.width(16.dp))
                Row(
                    modifier = Modifier.padding(vertical = 8.dp),
                ) {
                    it()
                }
            }
            Spacer(Modifier.width(16.dp))
            TrailingIconButton(expanded = expanded)
            Spacer(Modifier.width(16.dp))
        }
        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn(tween()) + expandVertically(tween()),
            exit = fadeOut(tween()) + shrinkVertically(tween()),
        ) {
            Column {
                LintHorizontalDivider()
                children()
            }
        }
    }
}

@Composable
fun LintItem(
    modifier: Modifier,
    icon: @Composable (() -> Unit)? = null,
    label: @Composable () -> Unit,
    tooltipText: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (RowScope.() -> Unit)? = null,
    onClick: (() -> Unit)? = null,
) {
    LintOutlineCard(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            icon?.also {
                Spacer(Modifier.width(16.dp))
                Surface(
                    modifier = Modifier.size(16.dp),
                    color = Color.Transparent,
                ) {
                    it()
                }
            }
            Spacer(Modifier.width(16.dp))
            Box(
                modifier = Modifier.fillMaxWidth().weight(1f),
                contentAlignment = Alignment.CenterStart
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text("", style = MaterialTheme.typography.titleMedium)
                    Text("", style = MaterialTheme.typography.labelMedium)
                }
                Column {
                    ProvideTextStyle(MaterialTheme.typography.titleMedium) {
                        label()
                    }
                    tooltipText?.also {
                        ProvideTextStyle(MaterialTheme.typography.labelMedium) {
                            it()
                        }
                    }
                }
            }
            trailingIcon?.also {
                Spacer(Modifier.width(16.dp))
                Row(
                    modifier = Modifier.padding(vertical = 8.dp),
                ) {
                    it()
                }
            }
            Spacer(Modifier.width(16.dp))
        }
    }
}