package br.com.movieapp.core.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import br.com.movieapp.core.domain.MovieSearch
import br.com.movieapp.search_movie_feature.domain.source.MovieSearchRemoteDataSource

class MovieSearchPagingSource(
    private val query: String,
    private val remoteDataSource: MovieSearchRemoteDataSource
) : PagingSource<Int, MovieSearch>() {
    override fun getRefreshKey(state: PagingState<Int, MovieSearch>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(LIMIT) ?: anchorPage?.nextKey?.minus(LIMIT)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieSearch> {
        return try {

            val pagerNumber = params.key ?: 1
            val response = remoteDataSource.getSearchMovies(page = pagerNumber, query = query)

            val movies = response.movies
            val totalPages = response.totalPage

            LoadResult.Page(
                data = movies,
                prevKey = if (pagerNumber == 1) null else pagerNumber - 1,
                nextKey = if (pagerNumber == totalPages) null else pagerNumber + 1
            )

        } catch (exception: Exception) {
            exception.printStackTrace()
            return LoadResult.Error(exception)
        }
    }

    companion object {
        private const val LIMIT = 20
    }

}