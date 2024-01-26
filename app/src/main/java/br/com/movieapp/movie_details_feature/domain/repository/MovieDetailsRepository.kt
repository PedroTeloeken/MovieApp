package br.com.movieapp.movie_details_feature.domain.repository

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import br.com.movieapp.core.domain.Movie
import br.com.movieapp.core.domain.MovieDetails
import kotlinx.coroutines.flow.Flow

interface MovieDetailsRepository {

    suspend fun getMovieDetails(movieId: Int): MovieDetails

    fun getMovieSimilar(movieId: Int): PagingSource<Int , Movie>

}