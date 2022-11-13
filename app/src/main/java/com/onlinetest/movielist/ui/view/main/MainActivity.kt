package com.onlinetest.movielist.ui.view.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.onlinetest.movielist.ui.theme.MovieListTheme
import com.onlinetest.movielist.ui.view.screen.MovieViewModel
import com.onlinetest.movielist.ui.view.screen.details.DetailsScreen
import com.onlinetest.movielist.ui.view.screen.list.MovieListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieListTheme {
                MainContent { trailerLink ->
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(trailerLink)))
                }
            }
        }
    }
}

@Composable
fun MainContent(onTrailerClicked: (link: String) -> Unit) {
    val navController = rememberNavController()
    val viewModel = hiltViewModel<MovieViewModel>()
    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(navController = navController, startDestination = "MovieList") {
            composable("MovieList") {
                MovieListScreen(navController, viewModel)
            }
            composable("details?id={id}", arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                    defaultValue = 1
                }

            )) {
                val movieId = it.arguments?.getInt("id")
                DetailsScreen(navController, movieId, viewModel) {
                    onTrailerClicked(it)
                }
            }
        }
    }
}