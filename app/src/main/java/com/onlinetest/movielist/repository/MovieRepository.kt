package com.onlinetest.movielist.repository

import com.onlinetest.movielist.datasource.TheDatabase
import com.onlinetest.movielist.model.Movie
import com.onlinetest.movielist.utils.Sort
import com.onlinetest.movielist.utils.Status
import javax.inject.Inject

class MovieRepository @Inject internal constructor(
    private val database: TheDatabase
) {
    suspend fun getMovies(sortBy: Sort): Status<List<Movie>> {
        val response = try {
            when (sortBy) {
                Sort.ByName -> database.movieDao.getAllMoviesByName()
                Sort.ByReleaseDate -> database.movieDao.getAllMoviesByReleaseDate()
                Sort.Default -> database.movieDao.getAllMovies()
            }

        } catch (e: Exception) {
            return Status.Error("Failed to load from database")
        }
        if (response.isEmpty())
            return Status.Error("Database is empty")
        return Status.Success(response)
    }

    suspend fun getMovieById(id: Int?): Status<Movie> {
        val response = try {
            database.movieDao.getMovieById(id)
        } catch (e: Exception) {
            return Status.Error("Failed to load from database")
        }
        return Status.Success(response)
    }

    suspend fun insertMoviesToDB(movies: List<Movie>) = database.movieDao.insertAllMovies(movies)

    suspend fun toggleAddToWatchList(movie: Movie) = database.movieDao.toggleAddToWatchList(movie)
}