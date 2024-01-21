package br.com.movieapp.movie_popular_feature.data.source

import br.com.movieapp.core.data.remote.service.MovieService
import br.com.movieapp.core.domain.MoviePaging
import br.com.movieapp.core.paging.MoviePagingSource
import br.com.movieapp.movie_popular_feature.data.mapper.toMovie
import br.com.movieapp.movie_popular_feature.domain.source.MoviePopularRemoteDataSource
import javax.inject.Inject

class MoviePopularRemoteDataSourceImpl @Inject constructor(
    private val service: MovieService
) : MoviePopularRemoteDataSource {
    override fun getPopularMoviesPagingSource(): MoviePagingSource {
        return MoviePagingSource(this)
    }

    override suspend fun getPopularMovies(page: Int): MoviePaging {
        val response = service.getPopularMovies(page)
        return MoviePaging(
            page = response.page,
            totalPage = response.totalPages,
            totalResults = response.totalResults,
            movies = response.results.map { it.toMovie() }
        )
    }
}