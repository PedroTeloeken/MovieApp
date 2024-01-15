package br.com.movieapp.movie_details_feature.presentacion

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val addMovieFavorite: AddMovieFavoriteUseCase,
    private val deleteMovieFavorite: DeleteMovieFavoriteUseCase,
    private val isMovieFavorite: IsFavoriteMoviesUseCase
) : ViewModel() {

    var uiState by mutableStateOf(MovieDetailState())
        private set

    fun getMovieDetail(getMovieDetails: MovieDetailEvent.GetMovieDetail) {
        event(getMovieDetails)
    }

    fun checkedFavorite(checkedFavorite: MovieDetailEvent.CheckedFavorite) {
        event(checkedFavorite)
    }

    private fun event(event: MovieDetailEvent) {
        when (event) {

            is MovieDetailEvent.CheckedFavorite -> {

            }

            is MovieDetailEvent.AddFavorite -> {
                viewModelScope.launch {
                    addMovieFavorite.invoke(
                        params = AddMovieFavoriteUseCase.Params(movie = event.movie)
                    ).collect { result ->

                            while (result) {
                                is ResultData.Success -> {

                                }
                                is ResultData.Failure -> {

                                }

                                is ResultData.Loading -> {

                                }
                            }

                        }
                }
            }

            is MovieDetailEvent.RemoveFavorite -> {

            }

            is MovieDetailEvent.GetMovieDetail -> {
                viewModelScope.launch {
                    getMovieDetailsUseCase.invoke(
                        params = GetMovieDetailsUseCase.Params(movieId = event.movieId)
                    ).collect { resultData ->
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
}