package br.com.movieapp.movie_details_feature.presentacion

import br.com.movieapp.core.domain.Movie

sealed class MovieDetailEvent {
    data class GetMovieDetail(val movieId: Int) : MovieDetailEvent()

    data class AddFavorite(val movie: Movie) : MovieDetailEvent()
    data class CheckedFavorite(val movieId: Int) : MovieDetailEvent()
    data class RemoveFavorite(val movie: Movie) : MovieDetailEvent()

}