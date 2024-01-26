package br.com.movieapp.movie_details_feature.presentacion

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import br.com.movieapp.core.domain.Movie
import br.com.movieapp.core.util.Constants
import br.com.movieapp.core.util.ResultData
import br.com.movieapp.core.util.UtilFunctions
import br.com.movieapp.movie_details_feature.domain.usecase.GetMovieDetailsUseCase
import br.com.movieapp.movie_details_feature.presentacion.state.MovieDetailState
import br.com.movieapp.movie_favorite_screen.domain.usecase.AddMovieFavoriteUseCase
import br.com.movieapp.movie_favorite_screen.domain.usecase.DeleteMovieFavoriteUseCase
import br.com.movieapp.movie_favorite_screen.domain.usecase.IsFavoriteMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val addMovieFavoriteUseCase: AddMovieFavoriteUseCase,
    private val deleteMovieFavoriteUseCase: DeleteMovieFavoriteUseCase,
    private val isMovieFavoriteUseCase: IsFavoriteMoviesUseCase,
    saveStateHandle: SavedStateHandle
) : ViewModel() {

    var uiState by mutableStateOf(MovieDetailState())
        private set


    private val movieId = saveStateHandle.get<Int>(key = Constants.MOVIE_DETAIL_ARGUMENT_KEY)

    init {
        movieId?.let {
            checkedFavorite(MovieDetailEvent.CheckedFavorite(it))
            getMovieDetail(MovieDetailEvent.GetMovieDetail(it))
        }
    }

    fun getMovieDetail(getMovieDetails: MovieDetailEvent.GetMovieDetail) {
        event(getMovieDetails)
    }

    fun checkedFavorite(checkedFavorite: MovieDetailEvent.CheckedFavorite) {
        event(checkedFavorite)
    }

    fun onAddFavorite(movie: Movie) {
        if (uiState.iconColor == Color.White) {
            Timber.i("Passei aqui para adicionar")
            event(MovieDetailEvent.AddFavorite(movie = movie))
        } else {
            Timber.i("Passei aqui para remover")
            event(MovieDetailEvent.RemoveFavorite(movie = movie))
        }
    }


    private fun pagingConfig(): PagingConfig {
        return PagingConfig(
            pageSize = 20,
            initialLoadSize = 20
        )
    }

    private fun event(event: MovieDetailEvent) {
        when (event) {

            is MovieDetailEvent.AddFavorite -> {
                viewModelScope.launch {
                    addMovieFavoriteUseCase.invoke(
                        params = AddMovieFavoriteUseCase.Params(movie = event.movie)
                    ).collectLatest { result ->
                        when (result) {
                            is ResultData.Success -> {
                                uiState = uiState.copy(iconColor = Color.Red)
                            }

                            is ResultData.Failure -> {
                                UtilFunctions.logError("Detail", "Erro ao cadastrar filme")
                            }

                            is ResultData.Loading -> {}
                        }
                    }
                }
            }

            is MovieDetailEvent.CheckedFavorite -> {
                viewModelScope.launch {
                    isMovieFavoriteUseCase.invoke(
                        params = IsFavoriteMoviesUseCase.Params(
                            movieId = event.movieId
                        )
                    ).collectLatest { result ->
                        when (result) {
                            is ResultData.Success -> {
                                uiState = if (result.data == true) {
                                    uiState.copy(iconColor = Color.Red)
                                } else {
                                    uiState.copy(iconColor = Color.White)
                                }
                            }

                            is ResultData.Failure -> {
                                UtilFunctions.logError(
                                    "Detail",
                                    "Erro ao checar se o filme é favorito"
                                )
                            }

                            is ResultData.Loading -> {}
                        }
                    }
                }
            }

            is MovieDetailEvent.RemoveFavorite -> {
                viewModelScope.launch {
                    deleteMovieFavoriteUseCase.invoke(
                        params = DeleteMovieFavoriteUseCase.Params(
                            movie = event.movie
                        )
                    ).collectLatest { result ->
                        when (result) {
                            is ResultData.Success -> {
                                uiState = uiState.copy(iconColor = Color.White)
                            }

                            is ResultData.Failure -> {
                                UtilFunctions.logError(
                                    "Detail",
                                    "Erro ao deletar"
                                )
                            }

                            is ResultData.Loading -> {
                            }
                        }
                    }
                }
            }

            is MovieDetailEvent.GetMovieDetail -> {
                viewModelScope.launch {
                    val resultData = getMovieDetailsUseCase.invoke(
                        params = GetMovieDetailsUseCase.Params(
                            movieId = event.movieId,
                            pagingConfig = pagingConfig()
                        )
                    )
                    when (resultData) {
                        is ResultData.Success -> {
                            uiState = uiState.copy(
                                isLoading = false,
                                movieDetails = resultData.data?.second,
                                results = resultData.data?.first ?: emptyFlow()
                            )
                        }

                        is ResultData.Failure -> {
                            uiState = uiState.copy(
                                isLoading = false,
                                error = resultData.e?.message.toString()
                            )
                            UtilFunctions.logError(
                                "DETAIL_ERROR",
                                resultData.e?.message.toString()
                            )
                        }

                        is ResultData.Loading -> {
                            uiState = uiState.copy(
                                isLoading = true
                            )
                        }
                    }
                }
            }
        }
    }
}