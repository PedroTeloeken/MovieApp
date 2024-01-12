package br.com.movieapp.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.movieapp.movie_popular_feature.presentacion.MoviePopularScreen
import br.com.movieapp.movie_popular_feature.presentacion.MoviePopularViewModel
import br.com.movieapp.search_movie_feature.presentacion.MovieSearchEvent
import br.com.movieapp.search_movie_feature.presentacion.MovieSearchScreen
import br.com.movieapp.search_movie_feature.presentacion.MovieSearchViewModel

@Composable
fun NavigationGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = BottomNavItem.MoviePopular.route
    ) {

        composable(BottomNavItem.MoviePopular.route) {

            val viewModel: MoviePopularViewModel = hiltViewModel()

            val uiState = viewModel.uiState

            MoviePopularScreen(
                uiState = uiState,
                navigateDetailMovie = {

                }
            )

        }

        composable(BottomNavItem.MovieSearch.route) {

            val viewModel: MovieSearchViewModel = hiltViewModel()
            val uiState = viewModel.uiState
            val onEvent: (MovieSearchEvent) -> Unit = viewModel::event
            val onFetch: (String) -> Unit = viewModel::fetch

            MovieSearchScreen(
                uiState = uiState,
                onEvent = onEvent,
                onFetch = onFetch,
                navigateToDetailMovie = {

                }
            )
        }

        composable(BottomNavItem.MovieFavorite.route) {

        }

    }

}