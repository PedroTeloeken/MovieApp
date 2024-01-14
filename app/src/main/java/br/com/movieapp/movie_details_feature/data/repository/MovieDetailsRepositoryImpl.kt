package br.com.movieapp.movie_details_feature.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import br.com.movieapp.core.domain.Movie
import br.com.movieapp.core.domain.MovieDetails
import br.com.movieapp.movie_details_feature.domain.repository.MovieDetailsRepository
import br.com.movieapp.movie_details_feature.domain.source.MovieDetailsRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieDetailsRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieDetailsRemoteDataSource
) : MovieDetailsRepository {
    override suspend fun getMovieDetails(movieId: Int): MovieDetails {
        return remoteDataSource.getMovieDetails(movieId = movieId)
    }

    override suspend fun getMovieSimilar(
        movieId: Int,
        pagingConfig: PagingConfig
    ): Flow<PagingData<Movie>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { remoteDataSource.getSimilarMoviePagingSource(movieId = movieId) }
        ).flow
    }
}