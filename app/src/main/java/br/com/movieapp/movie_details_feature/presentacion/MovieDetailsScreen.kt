package br.com.movieapp.movie_details_feature.presentacion

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.collectAsLazyPagingItems
import br.com.movieapp.R
import br.com.movieapp.movie_details_feature.presentacion.components.MovieDetailsContent
import br.com.movieapp.movie_details_feature.presentacion.state.MovieDetailState
import br.com.movieapp.ui.theme.black
import br.com.movieapp.ui.theme.white

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MovieDetailsScreen(
    id: Int?,
    uiState: MovieDetailState,
    getMovieDetail: (MovieDetailEvent.GetMovieDetail) -> Unit = {},
) {

    val pagingMovieSimilar = uiState.results.collectAsLazyPagingItems()

    LaunchedEffect(key1 = true) {
        if (id != null) {
            getMovieDetail(MovieDetailEvent.GetMovieDetail(id))
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.detail_movie), color = white)
                },
                backgroundColor = black,
            )
        }
    ) {
        MovieDetailsContent(
            movieDetails = uiState.movieDetails,
            pagingMoviesSimilar = pagingMovieSimilar,
            isLoading = uiState.isLoading,
            isError = uiState.error,
            iconColor = uiState.iconColor,
            onAddFavorite = {

            }
        )


    }

}