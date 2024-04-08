package io.github.lumkit.desktop.annotates

const val MODE_PUBLIC: Int = 0x0000

@IntDef(MODE_PUBLIC)
@Retention(AnnotationRetention.SOURCE)
annotation class PreferencesMode