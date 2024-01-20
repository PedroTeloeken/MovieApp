package br.com.movieapp.movie_favorite_screen.presentancion

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import br.com.movieapp.R
import br.com.movieapp.core.domain.Movie
import br.com.movieapp.core.presentation.components.common.MovieAppTopBar
import br.com.movieapp.movie_favorite_screen.presentancion.components.MovieFavoriteContent

@Composable
fun MovieFavoriteScreen(
    movies: List<Movie>,
    navigateToDetails: (Int) -> Unit = {}
) {

    Scaffold(topBar = {
        MovieAppTopBar(title = R.string.favorite_movies)
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