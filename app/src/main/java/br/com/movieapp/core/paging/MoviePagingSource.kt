package br.com.movieapp.core.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import br.com.movieapp.core.domain.Movie
import br.com.movieapp.movie_popular_feature.data.mapper.toMovie
import br.com.movieapp.movie_popular_feature.domain.source.MoviePopularRemoteDataSource
import okio.IOException
import retrofit2.HttpException

class MoviePagingSource(
    private val remoteDataSource: MoviePopularRemoteDataSource
) : PagingSource<Int, Movie>() {

    companion object {
        private const val LIMIT = 20
    }
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
       return state.anchorPosition?.let { anchorPosition ->
           val anchorPage = state.closestPageToPosition(anchorPosition)
           anchorPage?.prevKey?.plus(LIMIT) ?: anchorPage?.nextKey?.minus(LIMIT)
       }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {

            val pageNumber = params.key ?: 1

            val response = remoteDataSource.getPopularMovies(pageNumber)

            val movies = response.results

            LoadResult.Page(
                data = movies.toMovie(),
                prevKey = if (pageNumber == 1) null else pageNumber - 1,
                nextKey = if (movies.isEmpty()) null else pageNumber + 1
            )

        } catch (exception: IOException) {
            exception.printStackTrace()
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            exception.printStackTrace()
            return LoadResult.Error(exception)
        }
    }
}