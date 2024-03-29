package br.com.movieapp.movie_details_feature.domain.source

import br.com.movieapp.core.domain.MovieDetails
import br.com.movieapp.core.domain.MoviePaging
import br.com.movieapp.core.paging.MovieSimilarPagingSource

interface MovieDetailsRemoteDataSource {

    suspend fun getMovieDetails(movieId: Int): MovieDetails

    suspend fun getMoviesSimilar(page: Int, movieId: Int) : MoviePaging

    fun getSimilarMoviePagingSource(movieId: Int) : MovieSimilarPagingSource

}