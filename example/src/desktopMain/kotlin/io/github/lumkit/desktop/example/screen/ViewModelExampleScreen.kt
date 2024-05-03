package io.github.lumkit.desktop.example.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.lumkit.desktop.example.MyViewModel
import io.github.lumkit.desktop.lifecycle.viewModel
import io.github.lumkit.desktop.ui.components.LintButton
import io.github.lumkit.desktop.ui.components.LintTextField
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
@Composable
fun ViewModelExampleScreen() {
    val pageState = rememberPagerState { 2 }
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            LintButton(
                onClick = {
                    scope.launch { pageState.animateScrollToPage(0) }
                }
            ) {
                Text("跳转屏幕A")
            }
            LintButton(
                onClick = {
                    scope.launch { pageState.animateScrollToPage(1) }
                }
            ) {
                Text("跳转屏幕B")
            }
        }

        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            state = pageState,
        ) {
            when (it) {
                0 -> ScreenA()
                1 -> ScreenB()
            }
        }
    }
}

@Composable
private fun ScreenA() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val viewModel = viewModel<MyViewModel>()

        val text by viewModel.text.collectAsState()

        LintTextField(
            value = text,
            onValueChange = viewModel::setText,
            label = {
                Text("输入内容")
            }
        )
    }
}

@Composable
private fun ScreenB() {
    val viewModel = viewModel<MyViewModel>()
    val text by viewModel.text.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text, Modifier.padding(16.dp))
        LintButton(
            onClick = {
                viewModel.setText("")
            }
        ) {
            Text("清空内容")
        }
    }
}