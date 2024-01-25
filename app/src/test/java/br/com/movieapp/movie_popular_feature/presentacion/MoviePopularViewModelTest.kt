package br.com.movieapp.movie_popular_feature.presentacion

import androidx.paging.PagingData
import br.com.movieapp.TestDispatcherRule
import br.com.movieapp.core.domain.model.MovieFactory
import br.com.movieapp.movie_popular_feature.domain.usecase.GetMoviesPopularUseCase
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.any
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
import kotlin.RuntimeException

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MoviePopularViewModelTest {

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    @Mock
    lateinit var getPopularMovieUseCase: GetMoviesPopularUseCase

    private val viewModel by lazy {
        MoviePopularViewModel(getPopularMovieUseCase)
    }

    private val fakePagingDataMovies = PagingData.from(
        listOf(
            MovieFactory().create(poster = MovieFactory.Poster.Avengers),
            MovieFactory().create(poster = MovieFactory.Poster.JohnWick)
        )
    )

    @Test
    fun `must validate paging data object values when calling paging data from movies`() =
        runTest {

            //Given
            whenever(getPopularMovieUseCase(any())).thenReturn(flowOf(fakePagingDataMovies))

            //When
            val result = viewModel.uiState.movies.first()

            //than
            assertThat(result).isNotNull()

        }

    @Test(expected = RuntimeException::class)
    fun `must throw an exception when the calling to the use case returns an exception`() =
        runTest {

            //Given
            whenever(getPopularMovieUseCase.invoke(any())).thenThrow(
                RuntimeException()
            )

            //when
            val result = viewModel.uiState.movies.first()

            //then

            assertThat(result).isNull()

        }
}