package br.com.movieapp.movie_favorite_screen.presentancion

import br.com.movieapp.TestDispatcherRule
import br.com.movieapp.core.domain.model.MovieFactory
import br.com.movieapp.movie_favorite_screen.domain.usecase.GetMoviesFavoriteUseCase
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieFavoriteViewModelTest {

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    @Mock
    lateinit var getMovieFavoriteUseCase: GetMoviesFavoriteUseCase

    private val viewModel by lazy {
        MovieFavoriteViewModel(getMoviesFavoriteUseCase = getMovieFavoriteUseCase)
    }

    private val movieFavorite = listOf(
        MovieFactory().create(poster = MovieFactory.Poster.Avengers),
        MovieFactory().create(poster = MovieFactory.Poster.JohnWick)
    )

    @Test
    fun `must validate the data object values when calling list of favorites`() = runTest {

        //Given
        whenever(getMovieFavoriteUseCase.invoke()).thenReturn(
            flowOf(movieFavorite)
        )

        //when
        val result = viewModel.uiState.movies.first()

        //then
        assertThat(result).isNotEmpty()
        assertThat(result).contains(movieFavorite[0])

    }

}