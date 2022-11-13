package com.onlinetest.movielist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.onlinetest.movielist.datasource.TheDatabase
import com.onlinetest.movielist.model.Movie
import com.onlinetest.movielist.utils.toMilliSeconds
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(manifest = Config.NONE)
class DataBaseTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: TheDatabase
    private lateinit var movies: List<Movie>

    @Before
    fun initDb() {
        movies = listOf(
            Movie(
                1,
                "Tenet",
                "Armed with only one word, Tenet, and fighting for the survival of the entire world, a\n" +
                        "Protagonist journeys through a twilight world of international espionage on a mission that will\n" +
                        "unfold in something beyond real time.",
                7.8,
                "2h 30 min",
                "Action, Sci-Fi",
                "3 September 2020".toMilliSeconds(),
                "https://www.youtube.com/watch?v=LdOM0x0XDMo",
                R.drawable.img_tenet
            ),
            Movie(
                2,
                "Spider-Man: Into the Spider-Verse",
                "Teen Miles Morales becomes the Spider-Man of his universe, and must join with five\n" +
                        "spider-powered individuals from other dimensions to stop a threat for all realities.",
                8.4,
                "1h 57min",
                "Action, Animation, Adventure",
                "14 December 2018".toMilliSeconds(),
                " https://www.youtube.com/watch?v=tg52up16eq0",
                R.drawable.img_spider_man
            ),
            Movie(
                3,
                "Knives Out",
                "A detective investigates the death of a patriarch of an eccentric, combative family",
                7.9,
                "2h 10min",
                "Comedy, Crime, Drama",
                "27 November 2019".toMilliSeconds(),
                "https://www.youtube.com/watch?v=qGqiHJTsRkQ",
                R.drawable.img_knives_out
            ),
            Movie(
                4,
                "Guardians of the Galaxy",
                "A group of intergalactic criminals must pull together to stop a fanatical warrior with\n" +
                        "plans to purge the universe.",
                8.0,
                "2h 1min",
                "Adventure, Comedy",
                "1 August 2014".toMilliSeconds(),
                "https://www.youtube.com/watch?v=d96cjJhvlMA",
                R.drawable.img_guardians_of_galaxy
            ),
            Movie(
                5,
                "Avengers: Age of Ultron",
                "When Tony Stark and Bruce Banner try to jump-start a dormant peacekeeping\n" +
                        "program called Ultron, things go horribly wrong and it's up to Earth's mightiest heroes to stop the\n" +
                        "villainous Ultron from enacting his terrible plan.",
                7.3,
                "2h 21min",
                "Action, Adventure, Sci-Fi",
                "1 May 2015".toMilliSeconds(),
                "https://www.youtube.com/watch?v=tmeOjFno6Do",
                R.drawable.img_avengers
            )
        )

        database = Room.inMemoryDatabaseBuilder(
            getApplicationContext(),
            TheDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun `insert movies successfully`() {
        runBlockingTest {
            database.movieDao.insertAllMovies(movies)
            val movies = database.movieDao.getAllMovies()
            assertThat(movies.size).isNotEqualTo(0)
        }
    }

    @Test
    fun `add to watchlist successfully`() {
        runBlockingTest {
            database.movieDao.insertAllMovies(movies)
            val theMovie = database.movieDao.getMovieById(1)
            theMovie.isInWatchList = true
            database.movieDao.toggleAddToWatchList(theMovie)
            val updatedMovie = database.movieDao.getMovieById(1)
            assertThat(updatedMovie.isInWatchList).isEqualTo(true)
        }
    }

}