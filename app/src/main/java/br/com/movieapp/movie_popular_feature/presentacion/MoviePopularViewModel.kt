package br.com.movieapp.movie_popular_feature.presentacion

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import br.com.movieapp.movie_popular_feature.domain.usecase.GetMoviesPopularUseCase
import br.com.movieapp.movie_popular_feature.presentacion.state.MoviePopularState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviePopularViewModel @Inject constructor(
    getPopularMoviesUseCase: GetMoviesPopularUseCase
) : ViewModel() {

    var uiState by mutableStateOf(MoviePopularState())
        private set


    init {

        val movies = getPopularMoviesUseCase.invoke(
            params = GetMoviesPopularUseCase.Params(
                pagingConfig = pagingConfig()
            )
        ).cachedIn(viewModelScope)

        uiState = uiState.copy(movies = movies)

    }

    private fun pagingConfig() : PagingConfig {
        return PagingConfig(pageSize = 20 , initialLoadSize = 20)
    }

}