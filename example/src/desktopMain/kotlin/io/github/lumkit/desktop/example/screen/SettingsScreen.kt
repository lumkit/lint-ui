package io.github.lumkit.desktop.example.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BorderColor
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.isSecondaryPressed
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.dp
import io.github.lumkit.desktop.ui.components.LintFolder
import io.github.lumkit.desktop.ui.components.LintOutlineCard
import io.github.lumkit.desktop.ui.components.LintTextButton
import io.github.lumkit.desktop.ui.theme.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import lint_ui.example.generated.resources.*
import lint_ui.example.generated.resources.Res
import lint_ui.example.generated.resources.text_color_scheme
import lint_ui.example.generated.resources.text_color_scheme_settings
import lint_ui.example.generated.resources.text_settings
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import java.awt.Desktop
import java.net.URI
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class, ExperimentalLayoutApi::class)
@Composable
fun SettingsScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors().copy(containerColor = Color.Transparent),
            title = {
                Text(stringResource(Res.string.text_settings))
            }
        )
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val themeStore = LocalThemeStore.current
            var expanded by remember { mutableStateOf(true) }
            Spacer(Modifier)
            Text(stringResource(Res.string.text_color_scheme_settings), style = MaterialTheme.typography.bodyMedium)
            LintFolder(
                modifier = Modifier.fillMaxWidth(),
                expanded = expanded,
                icon = {
                    Icon(imageVector = Icons.Default.BorderColor, contentDescription = null)
                },
                label = {
                    Text(text = stringResource(Res.string.text_color_scheme))
                },
                trailingIcon = {
                    LintTextButton(
                        onClick = {
                            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                                try {
                                    Desktop.getDesktop().browse(URI("https://m3.material.io/theme-builder"))
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                        }
                    ) {
                        Text(stringResource(Res.string.text_set_color_theme))
                    }
                },
                onClick = {
                    expanded = !expanded
                }
            ) {
                val lintTheme = LocalLintTheme.current
                val themes = remember { mutableStateListOf<LintTheme.LintThemeColorSchemes>() }

                LaunchedEffect(Unit) {
                    withContext(Dispatchers.IO) {
                        if (themes.isEmpty()) {
                            themes.addAll(
                                lintTheme.loadThemesList().map {
                                    LintTheme.LintThemeColorSchemes(
                                        label = it.label,
                                        light = lintTheme lightColorScheme it.schemes.light,
                                        dark = lintTheme lightColorScheme it.schemes.dark,
                                    )
                                }
                            )
                        }
                    }
                }

                FlowRow(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ThemeItem(
                        lintTheme,
                        themeStore,
                        theme = DefaultLintTheme
                    )
                    themes.toList().forEachIndexed { index, lintThemeColorSchemes ->
                        ThemeItem(
                            lintTheme,
                            themeStore,
                            lintThemeColorSchemes
                        ) {
                            themes.removeAt(index)
                        }
                    }
                    InstallThemeItem(
                        lintTheme,
                        themes,
                        themeStore,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalResourceApi::class)
@Composable
private fun ThemeItem(
    lintTheme: LintTheme,
    themeStore: ThemeStore,
    theme: LintTheme.LintThemeColorSchemes,
    onUninstall: (() -> Unit)? = null
) {
    val isDarkTheme = themeStore.isDarkTheme
    var showMenu by remember { mutableStateOf(false) }
    Column {
        Box(
            modifier = Modifier.size(65.dp)
                .background(
                    color = if (isDarkTheme) {
                        theme.dark.primaryContainer
                    } else {
                        theme.light.primaryContainer
                    },
                    RoundedCornerShape(4.dp)
                ).clip(
                    RoundedCornerShape(4.dp)
                ).clickable {
                    themeStore.colorSchemes = theme
                }.onPointerEvent(
                    PointerEventType.Press
                ) {
                    if (it.buttons.isSecondaryPressed) {
                        showMenu = true
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Checkbox(
                modifier = Modifier.align(Alignment.TopEnd).padding(8.dp),
                checked = themeStore.colorSchemes.label == theme.label,
                onCheckedChange = null
            )
        }

        DropdownMenu(
            expanded = showMenu,
            onDismissRequest = { showMenu = false },
        ) {
            DropdownMenuItem(
                text = {
                    Text(text = stringResource(Res.string.text_uninstall_theme))
                },
                onClick = {
                    try {
                        lintTheme.uninstallThemeByName(theme.label ?: "") {
                            showMenu = false
                            onUninstall?.invoke()
                        }
                    }catch (e: Exception){
                        e.printStackTrace()
                    }
                }
            )
        }
    }
}

@Composable
private fun InstallThemeItem(
    lintTheme: LintTheme,
    themes: SnapshotStateList<LintTheme.LintThemeColorSchemes>,
    themeStore: ThemeStore, ) {
    val coroutineScope = rememberCoroutineScope { Dispatchers.IO }
    val fileChooser = remember {
        JFileChooser().apply {
            fileFilter = FileNameExtensionFilter("选择主题配置文件（.json）", "json")
            isAcceptAllFileFilterUsed = false
        }
    }
    LintOutlineCard(
        onClick = {
            val result = fileChooser.showOpenDialog(null)
            if (result == JFileChooser.APPROVE_OPTION) {
                coroutineScope.launch {
                    try {
                        val selectedFile = fileChooser.selectedFile
                        lintTheme.installTheme(selectedFile)
                        val themeBean = lintTheme.read(selectedFile.name)
                        lintTheme.useTheme(themeBean) {
                            themeStore.colorSchemes = it
                            themes.add(it)
                        }
                    }catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    ) {
        Box(
            modifier = Modifier.size(65.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
        }
    }
}