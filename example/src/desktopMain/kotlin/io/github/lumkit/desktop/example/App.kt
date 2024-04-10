package io.github.lumkit.desktop.example

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import io.github.lumkit.desktop.example.navigation.NavItem
import io.github.lumkit.desktop.example.navigation.screens
import io.github.lumkit.desktop.example.navigation.settingsNavigation
import io.github.lumkit.desktop.ui.components.*

@Composable
fun App() {
    val navItem = remember { mutableStateOf(screens.first()) }

    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        SideBar(navItem)
        LintCard(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(topStart = 6.dp, topEnd = 0.dp, bottomStart = 0.dp, bottomEnd = 0.dp),
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
        val width by remember { mutableStateOf(320.dp) }
        var searchValue by remember { mutableStateOf("") }

        LintNavigationIconButton(
            width = width,
            minimize = minimize.value,
            title = {
                Text("控制栏", softWrap = false)
            },
            onClick = {
                minimize.value = !minimize.value
            }
        ) {
            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
        }

        Column(
            horizontalAlignment = Alignment.End
        ) {
            LintSearchSide(
                width = width,
                minimize = minimize.value,
                icon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                },
                value = searchValue,
                onValueChange = {
                    searchValue = it
                },
                onClick = {
                    minimize.value = false
                },
                onClean = {
                    searchValue = ""
                },
            )
            DropdownMenu(
                searchValue.trim().isNotEmpty(),
                onDismissRequest = {},
                properties = PopupProperties(focusable = false)
            ) {
                for (i in 0 until 5) {
                    DropdownMenuItem(
                        text = {
                            Text("搜索项 ${i + 1}")
                        },
                        onClick = {
                            searchValue = ""
                        }
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxHeight().weight(1f)
        ) {
            val scrollState = rememberScrollState()
            val adapter = rememberScrollbarAdapter(scrollState)
            Column(
                modifier = Modifier.fillMaxHeight().verticalScroll(scrollState)
            ) {
                screens.forEach { screen ->
                    NavigationSide(width, minimize, navItem, screen)
                }
            }
            AnimatedVisibility(
                visible = !minimize.value,
            ) {
                LintVerticalScrollBar(
                    modifier = Modifier.padding(start = 4.dp),
                    adapter = adapter
                )
            }
        }

        LintSideNavigationBar(
            minimize = minimize.value,
            width = width,
            selected = navItem.value == settingsNavigation,
            onClick = {
                navItem.value = settingsNavigation
            },
            icon = {
                Icon(imageVector = Icons.Default.Settings, contentDescription = null)
            },
            title = {
                Text(settingsNavigation.title, softWrap = false)
            }
        )
        Spacer(Modifier)
    }
}

@Composable
private fun NavigationSide(
    width: Dp,
    minimize: MutableState<Boolean>,
    navItemState: MutableState<NavItem>,
    navItem: NavItem
) {
    var isExpanded by remember { mutableStateOf(true) }
    val navItems = navItem.items
    LintSideNavigationBar(
        width = width,
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
                    NavigationSide(width, minimize, navItemState, it)
                }
            }
        }
    )
}