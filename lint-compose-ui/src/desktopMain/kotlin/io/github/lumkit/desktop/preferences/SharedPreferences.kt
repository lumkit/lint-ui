package io.github.lumkit.desktop.preferences

import androidx.compose.runtime.compositionLocalOf
import io.github.lumkit.desktop.annotates.MODE_DATABASE
import io.github.lumkit.desktop.context.Context
import io.github.lumkit.desktop.data.SharedPreference
import io.github.lumkit.desktop.util.json
import kotlinx.serialization.encodeToString
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File

val LocalSharedPreferences = compositionLocalOf<SharedPreferences> { error("Not provided.") }

class SharedPreferences internal constructor(
    private val context: Context,
    private val name: String,
) {
    private val db by lazy {
        Database.connect("jdbc:sqlite:${File(context.getDir(MODE_DATABASE), "shared").absolutePath}.db", "org.sqlite.JDBC")
    }

    private val sharedPreferenceBean by lazy { SharedPreference(name) }

    init {
        transaction(db) {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(sharedPreferenceBean)
        }
    }

    fun getString(key: String): String? = transaction(db) {
        sharedPreferenceBean.selectAll()
            .where { sharedPreferenceBean.key eq key }
            .firstOrNull()
            ?.get(sharedPreferenceBean.value)
    }

    fun putString(key: String, value: String?): Unit = transaction(db) {
        if (value == null) {
            sharedPreferenceBean.deleteWhere { sharedPreferenceBean.key eq key }
            return@transaction
        }
        if (sharedPreferenceBean.selectAll().where { sharedPreferenceBean.key eq key }.firstOrNull() == null) {
            sharedPreferenceBean.insert {
                it[this.key] = key
                it[this.value] = value
            }
        } else {
            sharedPreferenceBean.update({ sharedPreferenceBean.key eq key }) {
                it[this.value] = value
            }
        }
    }

    fun toMap(): Map<String, String?> = transaction(db) {
        val map = HashMap<String, String?>()
        sharedPreferenceBean.selectAll().forEach {
            map[it[sharedPreferenceBean.key]] = it[sharedPreferenceBean.value]
        }
        map
    }

    inline fun <reified T> get(key: String): T = json.decodeFromString(getString(key) ?: "")
    fun <T> getList(key: String): List<T> = json.decodeFromString(getString(key) ?: "")
    fun <K, V> getMap(key: String): Map<K, V> = json.decodeFromString(getString(key) ?: "")

    inline fun <reified T> put(key: String, value: T) {
        putString(key, json.encodeToString(value))
    }
}