package br.com.movieapp.search_movie_feature.presentacion

import androidx.paging.PagingData
import br.com.movieapp.TestDispatcherRule
import br.com.movieapp.core.domain.model.MovieSearchFactory
import br.com.movieapp.search_movie_feature.domain.usecase.GetMovieSearchUseCase
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

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieSearchViewModelTest {

    @get:Rule
    val dispatcherRule = TestDispatcherRule()


    @Mock
    lateinit var getSearchMoviesUseCase: GetMovieSearchUseCase

    //Existe a possibilidade de instanciar assim o view model
    /*@Before
    fun setup() {
        viewModel = MovieSearchViewModel(getSearchMoviesUseCase)
    }*/

    private val fakePagingDataMovies = PagingData.from(
        listOf(
            MovieSearchFactory().create(poster = MovieSearchFactory.Poster.Avengers),
            MovieSearchFactory().create(poster = MovieSearchFactory.Poster.JohnWick)
        )
    )

    private val viewModel by lazy {
        MovieSearchViewModel(getSearchMoviesUseCase)
    }

    @Test
    fun `must validate paging data object values when calling movie search paging data`() =
        runTest {

            //Given
            whenever(getSearchMoviesUseCase.invoke(any())).thenReturn(
                flowOf(
                    fakePagingDataMovies
                )
            )

            //when
            viewModel.fetch("")
            val result = viewModel.uiState.movies.first()

            //Then
            assertThat(result).isNotNull()

        }

    @Test(expected = RuntimeException::class)
    fun `must throw an exception when the calling to the use case returns an exception`() =
        runTest {

            //Given
            whenever(getSearchMoviesUseCase.invoke(any())).thenThrow(
                RuntimeException()
            )

            //when
            viewModel.fetch("")



        }

}