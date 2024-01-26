package br.com.movieapp.movie_favorite_screen.domain.usecase

import br.com.movieapp.TestDispatcherRule
import br.com.movieapp.core.domain.model.MovieFactory
import br.com.movieapp.core.util.ResultData
import br.com.movieapp.movie_favorite_screen.domain.repository.MovieFavoriteRepository
import com.google.common.truth.Truth
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
import java.lang.RuntimeException

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DeleteMovieFavoriteUseCaseImplTest {

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    @Mock
    lateinit var movieFavoriteRepository: MovieFavoriteRepository

    private val movie = MovieFactory().create(poster = MovieFactory.Poster.Avengers)

    private val deleteMovieUseCase by lazy {
        DeleteMovieFavoriteUseCaseImpl(movieFavoriteRepository = movieFavoriteRepository)
    }

    @Test
    fun `should return Success from ResultStatus when the repository return success equal to Unit`() =
        runTest {

            //Given
            whenever(movieFavoriteRepository.delete(movie = movie)).thenReturn(Unit)

            //When
            val result = deleteMovieUseCase.invoke(DeleteMovieFavoriteUseCase.Params(movie = movie))

            Truth.assertThat(result).isEqualTo(flowOf(ResultData.Success(Unit)))

        }

    @Test
    fun `should return Failure from ResultStatus when the repository throws an exception`() =
        runTest {

            //Given
            val exception = RuntimeException()

            whenever(movieFavoriteRepository.delete(movie = movie)).thenThrow(exception)

            val result = deleteMovieUseCase.invoke(
                params = DeleteMovieFavoriteUseCase.Params(movie = movie)
            ).first()

            Truth.assertThat(result).isEqualTo(ResultData.Failure(exception))

        }

}