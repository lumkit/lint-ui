package io.github.lumkit.desktop.util

import kotlinx.serialization.json.Json

val json by lazy {
    Json{
        ignoreUnknownKeys = true
    }
}