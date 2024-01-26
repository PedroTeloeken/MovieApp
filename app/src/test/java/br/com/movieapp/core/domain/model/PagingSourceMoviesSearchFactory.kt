package br.com.movieapp.core.domain.model

import androidx.paging.PagingSource
import androidx.paging.PagingState
import br.com.movieapp.core.domain.Movie
import br.com.movieapp.core.domain.MovieSearch

class PagingSourceMoviesSearchFactory {

    fun create(movies: List<MovieSearch>) = object : PagingSource<Int, MovieSearch>() {

        override fun getRefreshKey(state: PagingState<Int, MovieSearch>): Int = 1

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieSearch> {
            return LoadResult.Page(
                data = movies,
                prevKey = null,
                nextKey = null
            )
        }

    }

}