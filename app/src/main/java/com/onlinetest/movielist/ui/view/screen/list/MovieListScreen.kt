package com.onlinetest.movielist.ui.view.screen.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.onlinetest.movielist.R
import com.onlinetest.movielist.model.Movie
import com.onlinetest.movielist.ui.theme.white
import com.onlinetest.movielist.ui.view.composables.StateHandler
import com.onlinetest.movielist.ui.view.screen.MovieViewModel
import com.onlinetest.movielist.utils.Sort
import com.onlinetest.movielist.utils.toDateFormat

@Composable
fun MovieListScreen(navHostController: NavHostController? = null, viewModel: MovieViewModel) {
    val movies by viewModel.movies.collectAsState()
    var mDisplayMenu by remember { mutableStateOf(false) }

    Scaffold(topBar = {
        TopAppBar(
            elevation = 2.dp,
            title = { Text(text = stringResource(R.string.title_movies)) },
            actions = {
                TextButton(onClick = { mDisplayMenu = !mDisplayMenu }) {
                    Text(text = stringResource(R.string.sort), color = white)
                }
                DropdownMenu(
                    expanded = mDisplayMenu,
                    onDismissRequest = { mDisplayMenu = false }
                ) {
                    DropdownMenuItem(onClick = {
                        viewModel.loadMovies(Sort.ByReleaseDate)
                        mDisplayMenu = false
                    }) {
                        Text(text = stringResource(R.string.sort_by_release_date))
                    }
                    DropdownMenuItem(onClick = {
                        viewModel.loadMovies(Sort.ByName)
                        mDisplayMenu = false
                    }) {
                        Text(text = stringResource(R.string.sort_by_name))
                    }
                }
            }
        )
    }, content = {
        StateHandler(status = movies) { movies ->
            MovieList(movies = movies, navHostController = navHostController)
        }
    })
}

@Composable
fun MovieList(movies: List<Movie>, navHostController: NavHostController? = null) {
    LazyColumn {
        items(movies) { movie ->
            MovieRow(movie, navHostController)
        }
    }
}

@Composable
fun MovieRow(movie: Movie, navHostController: NavHostController?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.8f)
            .padding(4.dp)
            .clickable {
                navHostController?.navigate("details?id=${movie.id}")
            }
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Card(
                modifier = Modifier.weight(1f)
            ) {
                Image(
                    painter = painterResource(id = movie.image),
                    contentDescription = "movie_poster",
                    contentScale = ContentScale.FillBounds
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(2f),
                contentAlignment = Alignment.CenterStart
            ) {
                Column {
                    Text(
                        text = "${movie.title} (${movie.releaseDate.toDateFormat("yyyy")})",
                        fontWeight = FontWeight.Bold, style = MaterialTheme.typography.subtitle1
                    )
                    Text(
                        text = "${movie.duration} - ${movie.genre}",
                        style = MaterialTheme.typography.subtitle2
                    )

                    if (movie.isInWatchList) {
                        Spacer(
                            modifier = Modifier.height(32.dp)
                        )
                        Text(
                            text = stringResource(R.string.on_my_watch_list),
                            style = MaterialTheme.typography.caption
                        )
                    }
                }
            }
        }
    }
}
