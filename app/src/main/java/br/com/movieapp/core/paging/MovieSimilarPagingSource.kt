package br.com.movieapp.core.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import br.com.movieapp.core.domain.Movie
import br.com.movieapp.movie_details_feature.domain.source.MovieDetailsRemoteDataSource

class MovieSimilarPagingSource(
    private val remoteDataSource: MovieDetailsRemoteDataSource,
    private val movieId: Int
) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(LIMIT) ?: anchorPage?.nextKey?.minus(LIMIT)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {

        return try {

            val pagerNumber = params.key ?: 1

            val response = remoteDataSource
                .getMoviesSimilar(page = pagerNumber, movieId = movieId)

            val movies = response.movies
            val totalPage = response.totalPage

            LoadResult.Page(
                data = movies,
                prevKey = if (pagerNumber == 1) null else pagerNumber - 1,
                nextKey = if (pagerNumber == totalPage) null else pagerNumber + 1
            )

        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }

    }

    companion object {
        private const val LIMIT = 20
    }

}