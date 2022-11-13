package com.onlinetest.movielist.datasource

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update
import com.onlinetest.movielist.model.Movie

@Dao
interface MovieDao {
    @Query("select * from Movie")
    suspend fun getAllMovies(): List<Movie>

    @Query("select * from Movie order by releaseDate desc")
    suspend fun getAllMoviesByReleaseDate(): List<Movie>

    @Query("select * from Movie where id = :id")
    suspend fun getMovieById(id: Int?): Movie

    @Query("select * from Movie order by title asc")
    suspend fun getAllMoviesByName(): List<Movie>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllMovies(movies: List<Movie>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun toggleAddToWatchList(movie: Movie)

}

@Database(entities = [Movie::class], version = 1)
abstract class TheDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao
}

private lateinit var INSTANCE: TheDatabase

fun getDatabase(context: Context): TheDatabase {
    synchronized(TheDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                TheDatabase::class.java,
                "movies.db"
            ).build()
        }
    }
    return INSTANCE
}