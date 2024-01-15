package br.com.movieapp.movie_favorite_screen.data.repository

import br.com.movieapp.core.domain.Movie
import br.com.movieapp.movie_favorite_screen.domain.repository.MovieFavoriteRepository
import br.com.movieapp.movie_favorite_screen.domain.source.MovieFavoriteLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieFavoriteRepositoryImpl @Inject constructor(
    private val localDataSource: MovieFavoriteLocalDataSource
) : MovieFavoriteRepository {
    override fun getMovie(): Flow<List<Movie>> {
        return localDataSource.getMovie()
    }

    override suspend fun insert(movie: Movie) {
        localDataSource.insert(movie = movie)
    }

    override suspend fun delete(movie: Movie) {
        localDataSource.delete(movie = movie)
    }

    override suspend fun isFavorite(movieId: Int): Boolean {
       return localDataSource.isFavorite(movieId)
    }

}