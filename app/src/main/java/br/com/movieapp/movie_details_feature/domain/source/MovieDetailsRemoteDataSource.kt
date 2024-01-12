package br.com.movieapp.movie_details_feature.domain.source

import br.com.movieapp.core.domain.MovieDetails

interface MovieDetailsRemoteDataSource {

    suspend fun getMovieDetails(movieId: Int): MovieDetails

    suspend fun getMoviesSimilar(page: Int, movieId: Int)

}