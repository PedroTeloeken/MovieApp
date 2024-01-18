package br.com.movieapp.movie_favorite_screen.domain.usecase

import br.com.movieapp.core.domain.Movie
import br.com.movieapp.core.util.ResultData
import br.com.movieapp.movie_favorite_screen.domain.repository.MovieFavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface DeleteMovieFavoriteUseCase {
    suspend operator fun invoke(params: Params): Flow<ResultData<Unit>>
    data class Params(val movie: Movie)

}

class DeleteMovieFavoriteUseCaseImpl @Inject constructor(
    private val movieFavoriteRepository: MovieFavoriteRepository
) : DeleteMovieFavoriteUseCase {
    override suspend fun invoke(params: DeleteMovieFavoriteUseCase.Params): Flow<ResultData<Unit>> {
        return flow {
            val delete = movieFavoriteRepository.delete(params.movie)
            emit(ResultData.Success(delete))
        }.flowOn(Dispatchers.IO)
    }
}