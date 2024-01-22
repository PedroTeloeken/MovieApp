package br.com.movieapp.movie_details_feature.data.source

import br.com.movieapp.core.data.remote.service.MovieService
import br.com.movieapp.core.domain.MovieDetails
import br.com.movieapp.core.domain.MoviePaging
import br.com.movieapp.core.paging.MovieSimilarPagingSource
import br.com.movieapp.core.util.toBackDropUrl
import br.com.movieapp.movie_details_feature.domain.source.MovieDetailsRemoteDataSource
import br.com.movieapp.movie_popular_feature.data.mapper.toMovie
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
            genres = response.genres.map { it.name },
            releaseDate = response.releaseDate,
            backdropPathUrl = response.backdropPath.toBackDropUrl(),
            voteAverage = response.voteAverage,
            duration = response.runtime,
            voteCount = response.voteCount
        )

    }

    override suspend fun getMoviesSimilar(page: Int, movieId: Int): MoviePaging {
        val response = service.getMoviesSimilar(page = page, movieId = movieId)

        return MoviePaging(
            page = response.page,
            totalPage = response.totalPages,
            totalResults = response.totalResults,
            movies = response.results.map { it.toMovie() }
        )
    }

    override fun getSimilarMoviePagingSource(movieId: Int): MovieSimilarPagingSource {
        return MovieSimilarPagingSource(this, movieId = movieId)
    }
}