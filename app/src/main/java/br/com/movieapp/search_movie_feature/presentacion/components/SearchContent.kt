package br.com.movieapp.search_movie_feature.presentacion.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import br.com.movieapp.core.domain.MovieSearch
import br.com.movieapp.core.presentation.components.common.ErrorScreen
import br.com.movieapp.core.presentation.components.common.LoadingView
import br.com.movieapp.movie_popular_feature.presentacion.components.MovieItem
import br.com.movieapp.search_movie_feature.presentacion.MovieSearchEvent
import br.com.movieapp.ui.theme.black

@Composable
fun SearchContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    paddingMovies: LazyPagingItems<MovieSearch>,
    query: String,
    onSearch: (String) -> Unit = {},
    onEvent: (MovieSearchEvent) -> Unit = {},
    onDetails: (movieId: Int) -> Unit = {}
) {

    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(black),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        SearchComponents(
            query = query,
            onSearch = {
                isLoading = true
                onSearch(it)
            },
            onQueryChangeEvent = { onEvent(it) },
            modifier = Modifier.padding(8.dp)
        )

        Spacer(modifier = Modifier.padding(vertical = 12.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = paddingValues,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize(),
            content = {
                items(paddingMovies.itemCount) { index ->
                    val movie = paddingMovies[index]

                    movie?.let {
                        MovieItem(
                            id = it.id,
                            voteAverage = it.voteAverage,
                            imageUrl = it.imageUrl,
                            onClick = { onDetails(it) }
                        )
                    }
                    isLoading = false
                }
                paddingMovies.apply {
                    when {

                        isLoading -> {
                            item(span = {
                                GridItemSpan(maxLineSpan)
                            }) {
                                LoadingView()
                            }
                        }

                        loadState.append is LoadState.Error -> {
                            isLoading = false
                            item(span = {
                                GridItemSpan(maxLineSpan)
                            }) {
                                ErrorScreen(
                                    message = "Aconteceu algum problema, tente novamente",
                                    retry = { retry() }
                                )
                            }
                        }

                        loadState.refresh is LoadState.Error -> {
                            isLoading = false
                            item(span = {
                                GridItemSpan(maxLineSpan)
                            }) {
                                ErrorScreen(
                                    message = "Aconteceu algum problema, tente novamente",
                                    retry = { retry() }
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}