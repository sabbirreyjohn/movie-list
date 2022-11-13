package com.onlinetest.movielist.ui.view.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onlinetest.movielist.datasource.movieList
import com.onlinetest.movielist.model.Movie
import com.onlinetest.movielist.repository.MovieRepository
import com.onlinetest.movielist.utils.Sort
import com.onlinetest.movielist.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val repo: MovieRepository) : ViewModel() {
    private val _movies = MutableStateFlow<Status<List<Movie>>>(Status.Loading())
    val movies get() = _movies

    private val _details = MutableStateFlow<Status<Movie>>(Status.Loading())
    val details get() = _details

    private val _sortBy = MutableStateFlow<Sort>(Sort.Default)

    init {
        loadMovies(_sortBy.value)
    }

    fun loadMovies(sortBy: Sort) {
        viewModelScope.launch {
            repo.insertMoviesToDB(movieList)
            _sortBy.value = sortBy
            _movies.value = repo.getMovies(sortBy)
        }
    }

    fun loadDetails(id: Int?) {
        viewModelScope.launch {
            _details.value = repo.getMovieById(id)
        }
    }

    fun toggleAddToWatchList(movie: Movie) {
        viewModelScope.launch {
            repo.toggleAddToWatchList(movie)
            loadDetails(movie.id)
            loadMovies(_sortBy.value)
        }
    }
}