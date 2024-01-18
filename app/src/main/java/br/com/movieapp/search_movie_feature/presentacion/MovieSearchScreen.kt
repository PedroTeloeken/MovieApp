package br.com.movieapp.search_movie_feature.presentacion

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.paging.compose.collectAsLazyPagingItems
import br.com.movieapp.R
import br.com.movieapp.core.presentation.components.common.MovieAppTopBar
import br.com.movieapp.search_movie_feature.presentacion.components.SearchContent
import br.com.movieapp.search_movie_feature.presentacion.state.MovieSearchState

@Composable
fun MovieSearchScreen(
    uiState: MovieSearchState,
    onEvent: (MovieSearchEvent) -> Unit = {},
    onFetch: (String) -> Unit = {},
    navigateToDetailMovie: (Int) -> Unit = {}
) {

    val pagingMovies = uiState.movies.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            MovieAppTopBar(title = R.string.search_movies)
        }
    ) { paddingValues ->

        SearchContent(
            paddingValues = paddingValues,
            paddingMovies = pagingMovies,
            query = uiState.query,
            onSearch = { onFetch(it) },
            onEvent = { onEvent(it) },
            onDetails = { navigateToDetailMovie(it) }
        )

    }

}