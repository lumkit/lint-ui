import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.MenuBar
import io.github.lumkit.desktop.context.LocalContext
import io.github.lumkit.desktop.data.DarkThemeMode
import io.github.lumkit.desktop.example.App
import io.github.lumkit.desktop.lintApplication
import io.github.lumkit.desktop.ui.LintWindow
import io.github.lumkit.desktop.ui.theme.AnimatedLintTheme
import io.github.lumkit.desktop.ui.theme.LocalThemeStore
import lint_ui.example.generated.resources.*
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import java.awt.Dimension

@OptIn(ExperimentalResourceApi::class)
fun main() = lintApplication(
    packageName = "LintUIExample"
) {
    val context = LocalContext.current

    // Toggles the global dark theme mode.
    LintWindow(
        onCloseRequest = ::exitApplication,
        rememberSize = true,
        title = context.getPackageName(),
        icon = painterResource(Res.drawable.compose_multiplatform),
    ) {
        window.minimumSize = Dimension(800, 600)
        AnimatedLintTheme(
            modifier = Modifier.fillMaxSize(),
        ) {
            App()
        }

        // Toggles the global dark theme mode.
        val themeMode = LocalThemeStore.current
        MenuBar {
            Menu(stringResource(Res.string.text_dark_theme)) {
                DarkThemeMode.entries.forEach { entry ->
                    CheckboxItem(
                        when (entry) {
                            DarkThemeMode.SYSTEM -> stringResource(Res.string.text_theme_system)
                            DarkThemeMode.LIGHT -> stringResource(Res.string.text_theme_light)
                            DarkThemeMode.DARK -> stringResource(Res.string.text_theme_dark)
                        },
                        checked = themeMode.darkTheme == entry
                    ) {
                        themeMode.darkTheme = entry
                    }
                }
            }
        }
    }
}