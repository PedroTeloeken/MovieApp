package br.com.movieapp.movie_favorite_screen.domain.source

import br.com.movieapp.core.domain.Movie
import kotlinx.coroutines.flow.Flow

interface MovieFavoriteLocalDataSource {

    fun getMovie(): Flow<List<Movie>>

    suspend fun insert(movie: Movie)

    suspend fun delete(movie: Movie)

    suspend fun isFavorite(movieId: Int): Boolean

}