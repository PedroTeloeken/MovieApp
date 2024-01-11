package br.com.movieapp.movie_popular_feature.presentacion

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.collectAsLazyPagingItems
import br.com.movieapp.R
import br.com.movieapp.core.util.UtilFunctions
import br.com.movieapp.movie_popular_feature.presentacion.components.MovieContent
import br.com.movieapp.movie_popular_feature.presentacion.state.MoviePopularState
import br.com.movieapp.ui.theme.black
import br.com.movieapp.ui.theme.white

@Composable
fun MoviePopularScreen(
    uiState: MoviePopularState,
    navigateDetailMovie: (Int) -> Unit
) {

    val movies = uiState.movies.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.popular_movies),
                        color = white
                    )
                },
                backgroundColor = black
            )
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