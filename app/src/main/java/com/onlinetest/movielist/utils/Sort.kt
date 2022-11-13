package com.onlinetest.movielist.utils

sealed class Sort{
    object Default : Sort()
    object ByName: Sort()
    object ByReleaseDate: Sort()
}
