package io.github.lumkit.desktop.example

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import io.github.lumkit.desktop.example.navigation.NavItem
import io.github.lumkit.desktop.example.navigation.screens
import io.github.lumkit.desktop.example.navigation.settingsNavigation
import io.github.lumkit.desktop.ui.components.LintCard
import io.github.lumkit.desktop.ui.components.LintNavigationIconButton
import io.github.lumkit.desktop.ui.components.LintOutlineCard
import io.github.lumkit.desktop.ui.components.LintSideNavigationBar

@Composable
fun App() {
    val navItem = remember { mutableStateOf(screens.first()) }

    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        SideBar(navItem)
        LintCard(
            modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(8.dp)),
        ) {
            AnimatedContent(
                targetState = navItem.value.screen,
                transitionSpec = {
                    (fadeIn(tween(easing = FastOutSlowInEasing)) + expandVertically(tween(easing = FastOutSlowInEasing)) + scaleIn(
                        tween()
                    )).togetherWith(
                        shrinkVertically(tween(easing = FastOutSlowInEasing)) + fadeOut(
                            tween(easing = FastOutSlowInEasing)
                        ) + scaleOut(tween())
                    )
                }
            ) {
                it()
            }
        }
    }
}

@Composable
private fun SideBar(navItem: MutableState<NavItem>) {
    Column(
        modifier = Modifier.fillMaxHeight()
            .padding(start = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val minimize = remember { mutableStateOf(false) }

        LintNavigationIconButton(
            onClick = {
                minimize.value = !minimize.value
            }
        ) {
            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
        }

        Column(
            modifier = Modifier.fillMaxHeight().verticalScroll(rememberScrollState()).weight(1f)
        ) {
            screens.forEach { screen ->
                NavigationSide(minimize, navItem, screen)
            }
        }

        LintNavigationIconButton(
            selected = navItem.value == settingsNavigation,
            onClick = {
                navItem.value = settingsNavigation
            }
        ) {
            Icon(imageVector = Icons.Default.Settings, contentDescription = null)
        }
        Spacer(Modifier)
    }
}

@Composable
private fun NavigationSide(minimize: MutableState<Boolean>, navItemState: MutableState<NavItem>, navItem: NavItem) {
    var isExpanded by remember { mutableStateOf(true) }
    val navItems = navItem.items
    LintSideNavigationBar(
        minimize = minimize.value,
        selected = navItemState.value == navItem,
        expanded = isExpanded,
        icon = { navItem.icon?.invoke() },
        title = {
            Text(navItem.title, softWrap = false)
        },
        subtitle = if (navItem.subtitle == null) {
            null
        } else {
            {
                Text(navItem.subtitle, softWrap = false)
            }
        },
        onClick = {
            navItemState.value = navItem
        },
        onExpandedClick = {
            isExpanded = !isExpanded
        },
        child = if (navItems == null) {
            null
        } else {
            {
                navItems.forEach {
                    NavigationSide(minimize, navItemState, it)
                }
            }
        }
    )
}