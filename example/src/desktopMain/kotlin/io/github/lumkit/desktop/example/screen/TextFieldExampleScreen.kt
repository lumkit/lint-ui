package io.github.lumkit.desktop.example.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.lumkit.desktop.ui.components.LintOutlinedTextField
import io.github.lumkit.desktop.ui.components.LintTextField

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TextFieldExampleScreen() {
    FlowRow(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            var text by remember { mutableStateOf("") }
            Text("基础输入框")
            LintTextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = { text = it },
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            var text by remember { mutableStateOf("") }
            Text("描边输入框")
            LintOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("描边输入框")
                },
                value = text,
                onValueChange = { text = it },
            )
        }
    }
}