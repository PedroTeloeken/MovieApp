package br.com.movieapp.search_movie_feature.data.source

import br.com.movieapp.core.data.remote.response.SearchResponse
import br.com.movieapp.core.data.remote.service.MovieService
import br.com.movieapp.core.paging.MovieSearchPagingSource
import br.com.movieapp.search_movie_feature.domain.source.MovieSearchRemoteDataSource
import javax.inject.Inject

class MovieSearchRemoteDataSourceImpl @Inject constructor(
    private val service: MovieService
) : MovieSearchRemoteDataSource {

    override fun getSearchMoviePagingSource(query: String): MovieSearchPagingSource {
        return MovieSearchPagingSource(query, remoteDataSource = this)
    }

    override suspend fun getSearchMovies(page: Int, query: String): SearchResponse {
        return service.searchMovie(page = page , query = query)
    }
}