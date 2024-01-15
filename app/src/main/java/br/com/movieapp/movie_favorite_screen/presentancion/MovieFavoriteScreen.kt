package br.com.movieapp.movie_favorite_screen.presentancion

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import br.com.movieapp.R
import br.com.movieapp.movie_details_feature.presentacion.components.MovieInfoContent
import br.com.movieapp.movie_favorite_screen.presentancion.components.MovieFavoriteContent
import br.com.movieapp.movie_favorite_screen.presentancion.state.MovieFavoriteState
import br.com.movieapp.ui.theme.black
import br.com.movieapp.ui.theme.white

@Composable
fun MovieFavoriteScreen(
    uiState: MovieFavoriteState,
    navigateToDetails: (Int) -> Unit = {}
) {

    val movies = uiState.movies

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.favorite_movies),
                    color = white
                )
            },
            backgroundColor = black
        )
    }) { paddingValues ->

        MovieFavoriteContent(
            movies = movies,
            paddingValues = paddingValues,
            onClick = { movieId ->
                navigateToDetails(movieId)
            }
        )

    }

}