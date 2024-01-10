package br.com.movieapp.movie_popular_feature.domain.usecase

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import br.com.movieapp.core.domain.Movie
import br.com.movieapp.movie_popular_feature.domain.repository.MoviePopularRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


interface GetMoviesPopularUseCase {
    operator fun invoke(): Flow<PagingData<Movie>>
}

class GetMoviesPopularUseCaseImpl @Inject constructor(
    private val repository: MoviePopularRepository
) : GetMoviesPopularUseCase {
    override fun invoke(): Flow<PagingData<Movie>> {
        return repository.getPopularMovies(
            pagingConfig = PagingConfig(
                pageSize = 20,
                initialLoadSize = 20
            )
        )
    }

}