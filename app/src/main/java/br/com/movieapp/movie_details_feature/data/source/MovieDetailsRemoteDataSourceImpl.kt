package br.com.movieapp.movie_details_feature.data.source

import br.com.movieapp.core.data.remote.response.MovieResponse
import br.com.movieapp.core.data.remote.service.MovieService
import br.com.movieapp.core.domain.MovieDetails
import br.com.movieapp.core.paging.MovieSimilarPagingSource
import br.com.movieapp.core.util.toBackDropUrl
import br.com.movieapp.movie_details_feature.domain.source.MovieDetailsRemoteDataSource
import javax.inject.Inject

class MovieDetailsRemoteDataSourceImpl @Inject constructor(
private val service: MovieService
) : MovieDetailsRemoteDataSource {
    override suspend fun getMovieDetails(movieId: Int): MovieDetails {
        val response = service.getMovie(movieId = movieId)

        return MovieDetails(
            id = response.id,
            title = response.title,
            overview = response.overview,
            genres =  response.genres.map { it.name },
            releaseDate = response.releaseDate,
            backdropPathUrl = response.backdropPath.toBackDropUrl(),
            voteAverage = response.voteAverage,
            duration = response.runtime,
            voteCount = response.voteCount
        )

    }

    override suspend fun getMoviesSimilar(page: Int, movieId: Int): MovieResponse {
        return service.getMoviesSimilar(page = page , movieId = movieId)
    }

    override fun getSimilarMoviePagingSource(movieId: Int): MovieSimilarPagingSource {
       return MovieSimilarPagingSource(this , movieId = movieId)
    }
}