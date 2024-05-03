package io.github.lumkit.desktop.lifecycle

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import io.github.lumkit.desktop.context.LocalContextWrapper
import kotlin.reflect.full.createInstance

@Stable
abstract class ViewModel

@Composable
inline fun <reified T : ViewModel> viewModel(): T {
    val contextWrapper = LocalContextWrapper.current
    val viewModelPool = contextWrapper.viewModelPool
    val viewModelMap = viewModelPool.filter { it.key == T::class }
    val viewModel: T = if (viewModelMap.isNotEmpty()) {
        viewModelMap.map { it.value }.first() as T
    } else {
        val instance = T::class.createInstance()
        viewModelPool[instance::class] = instance
        instance
    }
    return remember { viewModel }
}