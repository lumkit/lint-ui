package io.github.lumkit.desktop.example.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SmartButton
import androidx.compose.material.icons.filled.Widgets
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.lumkit.desktop.example.screen.ButtonExampleScreen
import io.github.lumkit.desktop.example.screen.SettingsScreen

data class NavItem(
    val title: String,
    val subtitle: String? = null,
    val icon: @Composable (() -> Unit)? = null,
    val screen: @Composable () -> Unit,
    val items: Array<NavItem>? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NavItem

        if (title != other.title) return false
        if (subtitle != other.subtitle) return false
        if (icon != other.icon) return false
        if (screen != other.screen) return false
        if (items != null) {
            if (other.items == null) return false
            if (!items.contentEquals(other.items)) return false
        } else if (other.items != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + (subtitle?.hashCode() ?: 0)
        result = 31 * result + (icon?.hashCode() ?: 0)
        result = 31 * result + screen.hashCode()
        result = 31 * result + (items?.contentHashCode() ?: 0)
        return result
    }
}

val settingsNavigation = NavItem(
    title = "设置",
    screen = @Composable {
        SettingsScreen()
    },
)

val screens = arrayOf(
    NavItem(
        title = "小组件",
        subtitle = "一些常用的小组",
        icon = {
            Icon(Icons.Default.Widgets, contentDescription = null)
        },
        screen = @Composable {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("欢迎使用Lint UI Compose Framework")
            }
        },
        items = arrayOf(
            NavItem(
                title = "Lint Buttons",
                subtitle = "基于MD3色彩风格设计的各式按钮",
                icon = {
                    Icon(imageVector = Icons.Default.SmartButton, contentDescription = null)
                },
                screen = @Composable {
                    ButtonExampleScreen()
                },
            )
        ),
    )
)