package io.github.lumkit.desktop

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.application
import io.github.lumkit.desktop.annotates.MODE_FILES
import io.github.lumkit.desktop.context.Context
import io.github.lumkit.desktop.context.LocalContext
import io.github.lumkit.desktop.preferences.LocalSharedPreferences
import io.github.lumkit.desktop.preferences.SharedPreferences
import java.io.File

fun lintApplication(
    packageName: String = "Lint UI",
    exitProcessOnExit: Boolean = true,
    content: @Composable ApplicationScope.() -> Unit
) = application(exitProcessOnExit = exitProcessOnExit) {
    val context = object : Context() {
        override fun getPackageName(): String = packageName

        override fun getDir(name: String): File = File(appDir, name).apply {
            if (!exists()) mkdirs()
        }

        override fun getFilesDir(): File = getDir(MODE_FILES).apply {
            if (!exists()) mkdirs()
        }

        override fun getSharedPreferences(name: String): SharedPreferences = SharedPreferences(this, name)
    }

    CompositionLocalProvider(
        LocalContext provides context,
        LocalSharedPreferences provides context.getSharedPreferences("configuration")
    ) {
        content()
    }
}