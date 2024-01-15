package br.com.movieapp.movie_favorite_screen.presentancion.state

import br.com.movieapp.core.domain.Movie

data class MovieFavoriteState(
    val movies: List<Movie> = emptyList()
)
