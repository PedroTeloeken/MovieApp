package br.com.movieapp.movie_details_feature.presentacion

sealed class MovieDetailEvent {
    data class GetMovieDetail(val movieId: Int) : MovieDetailEvent()
}