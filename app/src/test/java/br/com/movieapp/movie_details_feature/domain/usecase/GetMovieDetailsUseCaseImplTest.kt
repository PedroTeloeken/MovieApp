package br.com.movieapp.movie_details_feature.domain.usecase

import androidx.paging.PagingConfig
import br.com.movieapp.TestDispatcherRule
import br.com.movieapp.core.domain.model.MovieDetailsFactory
import br.com.movieapp.core.domain.model.MovieFactory
import br.com.movieapp.core.domain.model.PagingSourceMoviesFactory
import br.com.movieapp.core.util.ResultData
import br.com.movieapp.movie_details_feature.domain.repository.MovieDetailsRepository
import br.com.movieapp.search_movie_feature.domain.repository.MovieSearchRepository
import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.lang.RuntimeException

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetMovieDetailsUseCaseImplTest {

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    @Mock
    lateinit var movieDetailsRepository: MovieDetailsRepository

    private val movieFactory = MovieFactory().create(MovieFactory.Poster.Avengers)

    private val movieDetailsFactory =
        MovieDetailsFactory().create(MovieDetailsFactory.Poster.Avengers)

    private val pagingSourceFake = PagingSourceMoviesFactory().create(
        listOf(movieFactory)
    )

    private val getMovieDetailsUseCase by lazy {
        GetMovieDetailsUseCaseImpl(movieDetailsRepository)
    }

    @Test
    fun `should return Success from from ResultStatus when get both request success`() = runTest {

        //Given
        whenever(movieDetailsRepository.getMovieDetails(movieId = movieFactory.id)).thenReturn(
            movieDetailsFactory
        )

        whenever(movieDetailsRepository.getMovieSimilar(movieFactory.id)).thenReturn(
            pagingSourceFake
        )

        //when
        val result = getMovieDetailsUseCase.invoke(
            GetMovieDetailsUseCase.Params(
                movieId = movieFactory.id,
                pagingConfig = PagingConfig(20, 20)
            )
        )

        //Then
        verify(movieDetailsRepository).getMovieDetails(movieId = movieFactory.id)
        verify(movieDetailsRepository).getMovieSimilar(movieId = movieFactory.id)

        Truth.assertThat(result).isNotNull()
        Truth.assertThat(result is ResultData.Success).isTrue()

    }

    @Test
    fun `should return Error from ResultStatus when get movieDetails request return error`() =
        runTest {

            //Given
            val exception = RuntimeException()
            whenever(movieDetailsRepository.getMovieDetails(movieId = movieDetailsFactory.id)).thenThrow(
                exception
            )

            //when
            val result = getMovieDetailsUseCase.invoke(
                GetMovieDetailsUseCase.Params(
                    movieId = movieFactory.id,
                    pagingConfig = PagingConfig(20, 20)
                )
            )

            //Then
            verify(movieDetailsRepository).getMovieDetails(movieId = movieFactory.id)
            Truth.assertThat(result is ResultData.Failure).isTrue()

        }

    @Test
    fun `should return a ResultStatus error when get movieDetails request returns error`() =
        runTest {

            //Given
            val exception = RuntimeException()
            whenever(movieDetailsRepository.getMovieDetails(movieId = movieDetailsFactory.id)).thenThrow(
                exception
            )

            //when
            val result = getMovieDetailsUseCase.invoke(
                GetMovieDetailsUseCase.Params(
                    movieId = movieFactory.id,
                    pagingConfig = PagingConfig(20, 20)
                )
            )

            //Then
            verify(movieDetailsRepository).getMovieDetails(movieFactory.id)
            Truth.assertThat(result is ResultData.Failure).isTrue()

        }

    @Test
    fun `should return a ResultStatus error when getting similar movies returns an error`() =
        runTest {

            //Given
            val exception = RuntimeException()
            whenever(movieDetailsRepository.getMovieDetails(movieId = movieDetailsFactory.id)).thenThrow(
                exception
            )

            //when
            val result = getMovieDetailsUseCase.invoke(
                GetMovieDetailsUseCase.Params(
                    movieId = movieFactory.id,
                    pagingConfig = PagingConfig(20, 20)
                )
            )

            verify(movieDetailsRepository).getMovieSimilar(movieFactory.id)
            Truth.assertThat(result is ResultData.Failure).isTrue()

        }

}