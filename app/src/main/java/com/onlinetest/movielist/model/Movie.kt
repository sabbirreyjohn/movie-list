package com.onlinetest.movielist.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movie(
    @PrimaryKey
    val id: Int,
    val title: String,
    val description: String,
    val rating: Double,
    val duration: String,
    val genre: String,
    val releaseDate: Long,
    val trailerLink: String,
    val image: Int,
    var isInWatchList: Boolean = false
)
