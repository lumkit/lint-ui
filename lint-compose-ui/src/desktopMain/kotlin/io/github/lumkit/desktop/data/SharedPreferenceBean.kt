package io.github.lumkit.desktop.data

import org.jetbrains.exposed.dao.id.IntIdTable

data class SharedPreference(
    val name: String,
): IntIdTable(name) {
    val key = varchar("name", 255).uniqueIndex()
    val value = text("value").nullable()
}