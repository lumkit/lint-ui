package io.github.lumkit.desktop.context

import androidx.compose.runtime.compositionLocalOf
import io.github.lumkit.desktop.Const
import io.github.lumkit.desktop.annotates.FileMode
import io.github.lumkit.desktop.annotates.PreferencesMode
import io.github.lumkit.desktop.preferences.SharedPreferences
import io.github.lumkit.desktop.ui.theme.LintTheme
import org.jetbrains.skiko.hostOs
import java.io.File
import java.nio.file.Paths

val LocalContext = compositionLocalOf<Context> { error("Not provided.") }

/**
 * Interface to global information about an application environment.
 */
abstract class Context {

    protected val appDir by lazy {
        val path = when {
            hostOs.isWindows -> System.getenv("LOCALAPPDATA") ?: Paths.get(
                System.getProperty("user.home"),
                "AppData",
                "Local"
            ).toString()

            hostOs.isMacOS -> Paths.get(System.getProperty("user.home"), "Library", "Caches").toString()
            else -> Paths.get(System.getProperty("user.home"), ".cache").toString()
        }
        val file = File(path, getPackageName())
        if (!file.exists()) file.mkdirs()
        file
    }

    /**
     * Return the name of this application's package.
     */
    abstract fun getPackageName(): String

    abstract fun getDir(@FileMode name: String): File

    abstract fun getFilesDir(): File

    abstract fun getSharedPreferences(name: String): SharedPreferences

//    abstract fun getVersion(): String
//
//    abstract fun getVersionName(): String
    abstract fun getTheme(sharedPreferences: SharedPreferences = getSharedPreferences(Const.APP_CONFIGURATION)): LintTheme
}