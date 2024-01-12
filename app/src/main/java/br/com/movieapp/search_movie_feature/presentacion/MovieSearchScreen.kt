package br.com.movieapp.search_movie_feature.presentacion

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.collectAsLazyPagingItems
import br.com.movieapp.R
import br.com.movieapp.search_movie_feature.presentacion.components.SearchContent
import br.com.movieapp.search_movie_feature.presentacion.state.MovieSearchState
import br.com.movieapp.ui.theme.black
import br.com.movieapp.ui.theme.white

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
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.search_movies),
                        color = white
                    )
                },
                backgroundColor = black
            )
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