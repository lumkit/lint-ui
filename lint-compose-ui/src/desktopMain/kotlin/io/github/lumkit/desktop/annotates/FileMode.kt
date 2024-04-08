package io.github.lumkit.desktop.annotates

const val MODE_ROOT = ""
const val MODE_FILES = "files"
const val MODE_CACHES = "caches"
const val MODE_DATABASE = "database"

@StringDef(
    MODE_ROOT,
    MODE_FILES,
    MODE_CACHES,
    MODE_DATABASE,
)
@Retention(AnnotationRetention.SOURCE)
annotation class FileMode
