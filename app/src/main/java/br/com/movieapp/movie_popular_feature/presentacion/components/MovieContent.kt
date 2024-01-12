package br.com.movieapp.movie_popular_feature.presentacion.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import br.com.movieapp.core.domain.Movie
import br.com.movieapp.core.presentation.components.common.ErrorScreen
import br.com.movieapp.core.presentation.components.common.LoadingView

@Composable
fun MovieContent(
    modifier: Modifier = Modifier,
    pagingMovies: LazyPagingItems<Movie>,
    paddingValues: PaddingValues,
    onClickMovie: (movieId: Int) -> Unit
) {

    Box(modifier = modifier.background(Color.Black)) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = paddingValues,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {

            items(pagingMovies.itemCount) { index ->
                val movie = pagingMovies[index]
                movie?.let { movie ->
                    MovieItem(
                        voteAverage = movie.voteAverage,
                        imageUrl = movie.imageUrl,
                        id = movie.id,
                        onClick = { onClickMovie(it) }
                    )
                }
            }
            pagingMovies.apply {

                when {
                    loadState.refresh is LoadState.Loading -> {
                        item(span = {
                            GridItemSpan(maxLineSpan)
                        }) {
                            LoadingView()
                        }
                    }

                    loadState.append is LoadState.Loading -> {
                        item(span = {
                            GridItemSpan(maxLineSpan)
                        }) {
                            LoadingView()
                        }
                    }

                    loadState.append is LoadState.Error -> {
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
    }
}