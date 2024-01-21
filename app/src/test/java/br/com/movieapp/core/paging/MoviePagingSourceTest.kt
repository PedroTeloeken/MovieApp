package br.com.movieapp.core.paging

import androidx.paging.PagingSource
import br.com.movieapp.TestDispatcherRule
import br.com.movieapp.core.domain.Movie
import br.com.movieapp.core.domain.model.MovieFactory
import br.com.movieapp.core.domain.model.MoviePagingFactory
import br.com.movieapp.movie_popular_feature.domain.source.MoviePopularRemoteDataSource
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.lang.RuntimeException

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MoviePagingSourceTest {

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    @Mock
    lateinit var remoteDataSource: MoviePopularRemoteDataSource

    private val movieFactory = MovieFactory()

    private val moviePagingFactory = MoviePagingFactory().create()

    private val moviePagingSource by lazy {
        MoviePagingSource(remoteDataSource = remoteDataSource)
    }

    @Test
    fun `must return a success load result when `() = runTest {

        //Given
        whenever(remoteDataSource.getPopularMovies(any()))
            .thenReturn(moviePagingFactory)

        //When
        val result = moviePagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )

        val resultExpected = listOf(
            movieFactory.create(MovieFactory.Poster.Avengers),
            movieFactory.create(MovieFactory.Poster.JohnWick)
        )

        //Then
        assertThat(
            PagingSource.LoadResult.Page(
                data = resultExpected,
                prevKey = null,
                nextKey = 2
            )
        ).isEqualTo(result)

    }

    @Test
    fun `must return a error load result when load is called`() = runTest {

        //Given
        val exception = RuntimeException()
        whenever(remoteDataSource.getPopularMovies(any()))
            .thenThrow(exception)

        //when
        val result = moviePagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )

        //then
        assertThat(PagingSource.LoadResult.Error<Int, Movie>(exception))
            .isEqualTo(result)

    }

}