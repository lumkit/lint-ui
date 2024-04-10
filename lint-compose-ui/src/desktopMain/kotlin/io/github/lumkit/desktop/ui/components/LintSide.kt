package io.github.lumkit.desktop.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times

private val LocalNavigationItemLevel = compositionLocalOf { 0 }
private val navHeight = 52.dp

@Composable
fun LintSideNavigationBar(
    modifier: Modifier = Modifier,
    minimize: Boolean = false,
    expanded: Boolean = true,
    selected: Boolean = false,
    width: Dp = 320.dp,
    icon: @Composable () -> Unit = {},
    title: @Composable () -> Unit,
    subtitle: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    onExpandedClick: (() -> Unit)? = null,
    child: @Composable (ColumnScope.() -> Unit)? = null
) {
    val animatedWidth by animateDpAsState(
        targetValue = if (minimize) navHeight else width,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )
    Column(
        modifier = modifier.width(animatedWidth).clip(RoundedCornerShape(6.dp)),
    ) {
        NavItem(expanded, selected, icon, title, subtitle, onClick, onExpandedClick, child)
        AnimatedVisibility(
            visible = expanded && !minimize && animatedWidth == width,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Spacer(modifier = Modifier)
                CompositionLocalProvider(
                    LocalNavigationItemLevel provides LocalNavigationItemLevel.current + 1,
                ) {
                    child?.invoke(this)
                }
            }
        }
    }
}

@Composable
fun LintNavigationIconButton(
    modifier: Modifier = Modifier.size(navHeight),
    width: Dp = 320.dp,
    minimize: Boolean = false,
    selected: Boolean = false,
    title: @Composable (() -> Unit)? = null,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    val onSelectedColor by animateColorAsState(
        targetValue = if (selected) {
            MaterialTheme.colorScheme.surfaceVariant
        } else {
            Color.Transparent
        },
        tween(durationMillis = 300, easing = LinearOutSlowInEasing)
    )

    val animatedWidth by animateDpAsState(
        if (minimize) navHeight else width,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )

    Row (
        modifier = Modifier.width(animatedWidth),
        verticalAlignment = Alignment.CenterVertically,
    ){
        Box(
            modifier = Modifier.then(modifier)
                .background(onSelectedColor, RoundedCornerShape(6.dp))
                .clip(RoundedCornerShape(6.dp))
                .clickable {
                    onClick()
                },
        ) {
            NavThumb(selected)
            Surface(
                modifier = Modifier.size(16.dp).align(Alignment.Center),
                color = Color.Transparent,
                content = content
            )
        }
        title?.also {
            AnimatedVisibility(
                !minimize,
            ) {
                Row {
                    Spacer(modifier = Modifier.size(16.dp))
                    ProvideTextStyle(value = MaterialTheme.typography.bodyMedium) {
                        it()
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}

@Composable
fun LintSearchSide(
    modifier: Modifier = Modifier,
    width: Dp = 320.dp,
    minimize: Boolean = false,
    value: String,
    onValueChange: (String) -> Unit,
    icon: @Composable () -> Unit,
    onClick: () -> Unit = {},
    onClean: () -> Unit = {},
) {
    val animatedWidth by animateDpAsState(
        if (minimize) navHeight else width,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )

    Row (
        modifier = Modifier.height(navHeight).width(animatedWidth)
            .clip(RoundedCornerShape(6.dp))
            .then(modifier)
    ) {
        AnimatedContent(
            targetState = minimize
        ) {
            if (it) {
                Box(
                    modifier = Modifier.size(navHeight)
                        .clip(RoundedCornerShape(6.dp))
                        .clickable {
                            onClick()
                        },
                ) {
                    Surface(
                        modifier = Modifier.size(16.dp).align(Alignment.Center),
                        color = Color.Transparent,
                        content = icon
                    )
                }
            } else {
                ProvideTextStyle(
                    MaterialTheme.typography.bodyMedium
                ) {
                    LintTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = value,
                        onValueChange = onValueChange,
                        trailingIcon = {
                            if (value.isNotEmpty()) {
                                Icon(
                                    modifier = Modifier.size(16.dp)
                                        .clip(CircleShape)
                                        .clickable {
                                            onClean()
                                        },
                                    imageVector = Icons.Default.Close,
                                    contentDescription = null,
                                )
                            }
                        },
                        singleLine = true
                    )
                }
            }
        }
    }
}

@Composable
private fun NavItem(
    expanded: Boolean,
    selected: Boolean,
    icon: @Composable () -> Unit,
    title: @Composable () -> Unit,
    subtitle: @Composable() (() -> Unit)?,
    onClick: (() -> Unit)?,
    onExpandedClick: (() -> Unit)?,
    child: @Composable() (ColumnScope.() -> Unit)?
) {
    val onSelectedColor by animateColorAsState(
        targetValue = if (selected) {
            MaterialTheme.colorScheme.surfaceVariant
        } else {
            Color.Transparent
        },
        tween(durationMillis = 300, easing = LinearOutSlowInEasing)
    )

    Box(
        modifier = Modifier.height(navHeight).wrapContentWidth()
            .background(onSelectedColor, RoundedCornerShape(6.dp))
            .clip(RoundedCornerShape(6.dp))
            .then(
                if (onClick == null) Modifier
                else Modifier.clickable(onClick = onClick)
            )
    ) {
        NavThumb(selected = selected)
        val marginStart = LocalNavigationItemLevel.current * 28.dp
        Row(
            modifier = Modifier.wrapContentWidth().padding(start = marginStart),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(navHeight),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    modifier = Modifier.padding(16.dp)
                        .size(16.dp),
                    color = Color.Transparent
                ) {
                    icon()
                }
            }
            NavLabel(expanded, title, subtitle, child, onExpandedClick)
        }
    }
}

@Composable
private fun BoxScope.NavThumb(
    selected: Boolean,
) {
    val height by animateDpAsState(
        targetValue = if (selected) {
            16.dp
        } else {
            0.dp
        },
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )
    Surface(
        modifier = Modifier.clip(RoundedCornerShape(1.dp))
            .width(3.dp)
            .height(height)
            .align(Alignment.CenterStart),
        color = MaterialTheme.colorScheme.primary,
    ) {}
}

@Composable
private fun RowScope.NavLabel(
    expanded: Boolean,
    title: @Composable () -> Unit,
    subtitle: @Composable (() -> Unit)?,
    child: @Composable (ColumnScope.() -> Unit)?,
    onExpandedClick: (() -> Unit)?
) {
    Row(
        modifier = Modifier.fillMaxWidth().weight(1f),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth().weight(1f),
        ) {
            ProvideTextStyle(MaterialTheme.typography.bodyMedium) {
                title()
            }
            subtitle?.also {
                ProvideTextStyle(MaterialTheme.typography.labelMedium) {
                    it()
                }
            }
        }
        Spacer(modifier = Modifier.size(16.dp))
        child?.run {
            TrailingIconButton(expanded = expanded, onExpandedClick)
            Spacer(modifier = Modifier.size(16.dp))
        }
    }
}

@Composable
internal fun TrailingIconButton(
    expanded: Boolean,
    onClick: (() -> Unit)? = null,
) {
    val rotation by animateFloatAsState(
        targetValue = if (expanded) 0f else -180f,
        animationSpec = tween(easing = LinearEasing)
    )
    Icon(
        imageVector = Icons.Filled.KeyboardArrowDown,
        contentDescription = null,
        modifier = Modifier.size(24.dp).rotate(rotation)
            .clip(CircleShape)
            .then(
                if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier
            )
    )
}