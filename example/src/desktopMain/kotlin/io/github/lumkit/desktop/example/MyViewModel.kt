package io.github.lumkit.desktop.example

import io.github.lumkit.desktop.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MyViewModel : ViewModel() {

    private val _text = MutableStateFlow("")
    val text: StateFlow<String> = _text.asStateFlow()

    fun setText(text: String) {
        _text.value = text
    }

}