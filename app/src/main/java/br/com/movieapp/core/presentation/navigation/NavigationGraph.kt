package br.com.movieapp.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.com.movieapp.core.util.Constants
import br.com.movieapp.movie_details_feature.presentacion.MovieDetailViewModel
import br.com.movieapp.movie_details_feature.presentacion.MovieDetailsScreen
import br.com.movieapp.movie_favorite_screen.presentancion.MovieFavoriteScreen
import br.com.movieapp.movie_favorite_screen.presentancion.MovieFavoriteViewModel
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
                    navController
                        .navigate(DetailsScreenNav.DetailsScreen.passMovieId(movieId = it))
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
                    navController
                        .navigate(DetailsScreenNav.DetailsScreen.passMovieId(movieId = it))
                }
            )
        }

        composable(BottomNavItem.MovieFavorite.route) {

            val viewModel: MovieFavoriteViewModel = hiltViewModel()
            val uiState =
                viewModel.uiState.movies.collectAsStateWithLifecycle(initialValue = emptyList())

            MovieFavoriteScreen(
                movies = uiState.value
            ) {
                navController.navigate(DetailsScreenNav.DetailsScreen.passMovieId(it))
            }

        }

        composable(
            route = DetailsScreenNav.DetailsScreen.route,
            arguments = listOf(
                navArgument(
                    Constants.MOVIE_DETAIL_ARGUMENT_KEY
                ) {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) {

            val viewModel: MovieDetailViewModel = hiltViewModel()
            val uiState = viewModel.uiState
            val onAddFavorite = viewModel::onAddFavorite

            MovieDetailsScreen(
                uiState = uiState,
                onAddFavorite = onAddFavorite

            )
        }
    }
}