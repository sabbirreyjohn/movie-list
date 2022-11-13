package com.onlinetest.movielist.ui.view.screen.details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.onlinetest.movielist.R
import com.onlinetest.movielist.ui.theme.black
import com.onlinetest.movielist.ui.view.composables.StateHandler
import com.onlinetest.movielist.ui.view.screen.MovieViewModel
import com.onlinetest.movielist.utils.toDateFormat

@Composable
fun DetailsScreen(
    navController: NavHostController,
    movieId: Int?,
    viewModel: MovieViewModel,
    onTrailerClicked: (link: String) -> Unit
) {
    val details by viewModel.details.collectAsState()
    viewModel.loadDetails(movieId)

    Scaffold(topBar = {
        TopAppBar(
            elevation = 2.dp,
            title = { Text(text = stringResource(id = R.string.title_movies)) },
            navigationIcon = if (navController.previousBackStackEntry != null) {
                {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            } else {
                null
            }

        )
    }, content = {
        StateHandler(status = details) { detail ->
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(8.dp)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Card {
                        Image(
                            painter = painterResource(id = detail.image),
                            contentDescription = "movie_poster",
                            modifier = Modifier
                                .width(160.dp)
                                .aspectRatio(.7f),
                            contentScale = ContentScale.FillBounds
                        )
                    }
                    Column {
                        Spacer(modifier = Modifier.height(8.dp))
                        Row {
                            Text(
                                text = detail.title,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.h6
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = "${detail.rating}", fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.h6
                            )
                            Text(text = stringResource(R.string.out_of_ten))
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                        OutlinedButton(onClick = {
                            detail.isInWatchList = !detail.isInWatchList
                            viewModel.toggleAddToWatchList(detail)
                        }, border = BorderStroke(1.dp, black)) {
                            Text(
                                text = if (!detail.isInWatchList) stringResource(R.string.add_to_watch_list) else stringResource(
                                    R.string.remove_from_watch_list
                                ),
                                style = MaterialTheme.typography.caption
                            )
                        }
                        OutlinedButton(onClick = {
                            onTrailerClicked(detail.trailerLink)
                        }, border = BorderStroke(1.dp, black)) {
                            Text(
                                text = stringResource(R.string.watch_trailer),
                                style = MaterialTheme.typography.caption
                            )
                        }
                    }

                }
                Divider()
                Column {
                    Text(
                        text = stringResource(R.string.short_description),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.body1
                    )
                    Text(text = detail.description, style = MaterialTheme.typography.subtitle2)

                }
                Divider()
                Column {
                    Text(
                        text = stringResource(R.string.details), fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.body1
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = stringResource(R.string.genre),
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.subtitle2
                        )
                        Text(
                            text = detail.genre,
                            style = MaterialTheme.typography.subtitle2
                        )

                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = stringResource(R.string.release_date),
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.subtitle2
                        )
                        Text(
                            text = detail.releaseDate.toDateFormat("yyyy d MMMM"),
                            style = MaterialTheme.typography.subtitle2
                        )

                    }

                }
            }
        }
    })


}