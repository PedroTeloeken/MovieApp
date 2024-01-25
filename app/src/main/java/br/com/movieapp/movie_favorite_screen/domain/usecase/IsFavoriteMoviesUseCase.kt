package br.com.movieapp.movie_favorite_screen.domain.usecase

import br.com.movieapp.core.util.ResultData
import br.com.movieapp.movie_favorite_screen.domain.repository.MovieFavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface IsFavoriteMoviesUseCase {
    suspend operator fun invoke(params: Params): Flow<ResultData<Boolean>>
    data class Params(val movieId: Int)

}

class IsFavoriteMoviesUseCaseImpl @Inject constructor(
    private val movieFavoriteRepository: MovieFavoriteRepository
) : IsFavoriteMoviesUseCase {
    override suspend fun invoke(params: IsFavoriteMoviesUseCase.Params): Flow<ResultData<Boolean>> {
        return flow {

            try {
                emit(ResultData.Success(movieFavoriteRepository.isFavorite(params.movieId)))
            } catch (e: Exception) {
                emit(ResultData.Failure(e))
            }
        }.flowOn(Dispatchers.IO)
    }
}