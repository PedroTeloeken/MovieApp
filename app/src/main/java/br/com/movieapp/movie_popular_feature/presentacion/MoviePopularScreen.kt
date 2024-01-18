package br.com.movieapp.movie_popular_feature.presentacion

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.paging.compose.collectAsLazyPagingItems
import br.com.movieapp.R
import br.com.movieapp.core.presentation.components.common.MovieAppTopBar
import br.com.movieapp.core.util.UtilFunctions
import br.com.movieapp.movie_popular_feature.presentacion.components.MovieContent
import br.com.movieapp.movie_popular_feature.presentacion.state.MoviePopularState

@Composable
fun MoviePopularScreen(
    uiState: MoviePopularState,
    navigateDetailMovie: (Int) -> Unit
) {

    val movies = uiState.movies.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            MovieAppTopBar(title = R.string.popular_movies)
        },
        content = { paddingValues ->
            MovieContent(
                pagingMovies = movies,
                paddingValues = paddingValues,
                onClickMovie = { movieId ->
                    UtilFunctions.logInfo("Movie_Id", movieId.toString())
                    navigateDetailMovie(movieId)
                }
            )
        }
    )

}