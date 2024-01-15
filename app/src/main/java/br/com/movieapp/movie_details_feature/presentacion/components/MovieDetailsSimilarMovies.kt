package br.com.movieapp.movie_details_feature.presentacion.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import br.com.movieapp.core.domain.Movie
import br.com.movieapp.core.presentation.components.common.ErrorScreen
import br.com.movieapp.core.presentation.components.common.LoadingView
import br.com.movieapp.movie_popular_feature.presentacion.components.MovieItem

@Composable
fun MovieDetailsSimilarMovies(
    modifier: Modifier = Modifier,
    pagingMoviesSimilar: LazyPagingItems<Movie>,
) {

    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
    ) {

        items(pagingMoviesSimilar.itemCount) {
            val movie = pagingMoviesSimilar[it]

            movie?.let {
                MovieItem(
                    voteAverage = it.voteAverage,
                    imageUrl = it.imageUrl,
                    id = it.id
                )
            }
        }
        pagingMoviesSimilar.apply {
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

                loadState.refresh is LoadState.Error -> {
                    val error = pagingMoviesSimilar.loadState.refresh as LoadState.Error
                    item(span = {
                        GridItemSpan(maxLineSpan)
                    }) {
                        ErrorScreen(message = error.error.toString()) {
                            retry()
                        }
                    }
                }

                loadState.append is LoadState.Error -> {
                    val error = pagingMoviesSimilar.loadState.refresh as LoadState.Error
                    item(span = {
                        GridItemSpan(maxLineSpan)
                    }) {
                        ErrorScreen(message = error.error.toString()) {
                            retry()
                        }
                    }
                }
            }
        }
    }

}