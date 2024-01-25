package br.com.movieapp.movie_favorite_screen.domain.usecase

import br.com.movieapp.TestDispatcherRule
import br.com.movieapp.core.domain.model.MovieFactory
import br.com.movieapp.movie_favorite_screen.domain.repository.MovieFavoriteRepository
import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.lang.RuntimeException

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetMoviesFavoriteUseCaseImplTest {

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    @Mock
    lateinit var movieFavoriteRepository: MovieFavoriteRepository

    private val movies = listOf(
        MovieFactory().create(poster = MovieFactory.Poster.Avengers),
        MovieFactory().create(poster = MovieFactory.Poster.JohnWick)
    )

    private val getMoviesFavoriteUseCase by lazy {
        GetMoviesFavoriteUseCaseImpl(movieFavoriteRepository = movieFavoriteRepository)
    }

    @Test
    fun `should return Success from resultStatus when the repository return a list of movies`() =
        runTest {

            whenever(movieFavoriteRepository.getMovie()).thenReturn(flowOf(movies))

            val result = getMoviesFavoriteUseCase.invoke().first()

            Truth.assertThat(result).isNotEmpty()
            Truth.assertThat(result).contains(movies[1])

        }

    @Test
    fun `should emit an empty stream when exception is throw when calling the invoke method`() =
        runTest {

            //Given
            val exception = RuntimeException()
            whenever(movieFavoriteRepository.getMovie()).thenThrow(exception)

            //When
            val result = getMoviesFavoriteUseCase.invoke().toList()

            //then
            verify(movieFavoriteRepository).getMovie()
            Truth.assertThat(result).isEmpty()

        }

}