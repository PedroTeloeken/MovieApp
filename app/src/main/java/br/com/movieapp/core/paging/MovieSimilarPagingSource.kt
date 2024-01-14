package br.com.movieapp.core.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import br.com.movieapp.core.domain.Movie
import br.com.movieapp.movie_details_feature.domain.source.MovieDetailsRemoteDataSource
import br.com.movieapp.movie_popular_feature.data.mapper.toMovie
import retrofit2.HttpException
import java.io.IOException

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

            val movies = remoteDataSource
                .getMoviesSimilar(page = pagerNumber, movieId = movieId)
                .results.toMovie()

            LoadResult.Page(
                data = movies,
                prevKey = if (pagerNumber == 1) null else pagerNumber - 1,
                nextKey = if (movies.isEmpty()) null else pagerNumber + 1
            )

        } catch (exception: IOException) {
            exception.printStackTrace()
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            exception.printStackTrace()
            return LoadResult.Error(exception)
        }

    }

    companion object {
        private const val LIMIT = 20
    }

}