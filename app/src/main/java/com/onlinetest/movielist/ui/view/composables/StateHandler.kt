package com.onlinetest.movielist.ui.view.composables

import androidx.compose.runtime.Composable
import com.onlinetest.movielist.utils.Status

@Composable
fun <T> StateHandler(
    status: Status<T>,
    loading: @Composable () -> Unit = { ShowLoading() },
    error: @Composable (String) -> Unit = { ShowError(it) },
    content: @Composable (T) -> Unit,
) {
    when (status) {
        is Status.Error -> error(status.message!!)
        is Status.Loading -> loading()
        is Status.Success -> content(status.data as T)
    }
}