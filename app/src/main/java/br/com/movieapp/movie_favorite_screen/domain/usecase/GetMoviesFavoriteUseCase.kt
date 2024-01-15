package br.com.movieapp.movie_favorite_screen.domain.usecase

import br.com.movieapp.core.domain.Movie
import br.com.movieapp.movie_favorite_screen.domain.repository.MovieFavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetMoviesFavoriteUseCase {
    suspend operator fun invoke(): Flow<List<Movie>>

}

class GetMoviesFavoriteUseCaseImpl @Inject constructor(
    private val movieFavoriteRepository: MovieFavoriteRepository
) : GetMoviesFavoriteUseCase {
    override suspend fun invoke(): Flow<List<Movie>> {
        return movieFavoriteRepository.getMovie()
    }

}