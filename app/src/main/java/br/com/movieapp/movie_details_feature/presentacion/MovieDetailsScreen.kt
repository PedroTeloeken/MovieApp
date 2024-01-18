package br.com.movieapp.movie_details_feature.presentacion

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.paging.compose.collectAsLazyPagingItems
import br.com.movieapp.R
import br.com.movieapp.core.domain.Movie
import br.com.movieapp.core.presentation.components.common.MovieAppTopBar
import br.com.movieapp.movie_details_feature.presentacion.components.MovieDetailsContent
import br.com.movieapp.movie_details_feature.presentacion.state.MovieDetailState

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MovieDetailsScreen(
    uiState: MovieDetailState,
    onAddFavorite: (Movie) -> Unit = {},
) {

    val pagingMovieSimilar = uiState.results.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            MovieAppTopBar(title = R.string.detail_movie)
        }
    ) {
        MovieDetailsContent(
            movieDetails = uiState.movieDetails,
            pagingMoviesSimilar = pagingMovieSimilar,
            isLoading = uiState.isLoading,
            isError = uiState.error,
            iconColor = uiState.iconColor,
            onAddFavorite = {
                onAddFavorite(it)
            }
        )
    }
}