package br.com.movieapp.search_movie_feature.data.repository

import androidx.paging.PagingSource
import br.com.movieapp.core.domain.MovieSearch
import br.com.movieapp.core.paging.MovieSearchPagingSource
import br.com.movieapp.search_movie_feature.domain.repository.MovieSearchRepository
import br.com.movieapp.search_movie_feature.domain.source.MovieSearchRemoteDataSource
import javax.inject.Inject

class MovieSearchRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieSearchRemoteDataSource
) : MovieSearchRepository {

    override fun getSearchMovies(
        query: String
    ): PagingSource<Int, MovieSearch> {
        return MovieSearchPagingSource(query, remoteDataSource)

    }
}