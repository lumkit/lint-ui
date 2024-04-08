import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.lumkit.desktop.lintApplication
import io.github.lumkit.desktop.preferences.LocalSharedPreferences
import io.github.lumkit.desktop.ui.LintRememberWindow
import io.github.lumkit.desktop.ui.LintWindow

fun main() = lintApplication(
    packageName = "LintUIExample"
) {
    val sharedPreferences = LocalSharedPreferences.current

    var key by remember { mutableStateOf("") }
    var value by remember { mutableStateOf("") }
    val map = remember { mutableStateMapOf<String, String?>() }

    LintRememberWindow(onCloseRequest = ::exitApplication, title = "lint ui") {
        Surface(

        ) {
            Column {
                Row {
                    OutlinedTextField(
                        label = {
                            Text("Key")
                        },
                        value = key,
                        onValueChange = {
                            key = it
                        }
                    )
                    Spacer(Modifier.width(8.dp))
                    OutlinedTextField(
                        label = {
                            Text("Value")
                        },
                        value = value,
                        onValueChange = {
                            value = it
                        }
                    )
                    Spacer(Modifier.width(8.dp))
                    Button(onClick = {
                        sharedPreferences.putString(key, value)
                        key = ""
                        value = ""
                    }) {
                        Text("Submit")
                    }
                }
                Spacer(Modifier.width(8.dp))
                Button(onClick = {
                    val mapTest = mapOf("hello" to "world", "this is" to "compose desktop")
                    sharedPreferences.put("hello", mapTest)
                }) {
                    Text("Put Map Test")
                }
                Spacer(Modifier.width(8.dp))
                Button(onClick = {
                    map.putAll(sharedPreferences.getMap("hello"))
                }) {
                    Text("Change This Map")
                }
                Spacer(Modifier.width(8.dp))
                Button(onClick = {
                    map.clear()
                    map.putAll(sharedPreferences.toMap())
                }) {
                    Text("Select All")
                }
                Spacer(Modifier.width(8.dp))
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    items(map.entries.toList()) {
                        Text("Key: ${it.key}  Value: ${it.value}")
                    }
                }
            }
        }
    }
}