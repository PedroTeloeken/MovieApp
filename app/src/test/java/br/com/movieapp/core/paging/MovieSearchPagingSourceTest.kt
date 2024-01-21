package br.com.movieapp.core.paging

import androidx.paging.PagingSource
import br.com.movieapp.TestDispatcherRule
import br.com.movieapp.core.domain.MovieSearch
import br.com.movieapp.core.domain.model.MovieSearchFactory
import br.com.movieapp.core.domain.model.MovieSearchPagingFactory
import br.com.movieapp.search_movie_feature.domain.source.MovieSearchRemoteDataSource
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
class MovieSearchPagingSourceTest {

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    @Mock
    lateinit var remoteDataSource: MovieSearchRemoteDataSource

    private val movieSearchFactory = MovieSearchFactory()

    private val movieSearchPagingFactory = MovieSearchPagingFactory().create()

    private val movieSearchPaging = MovieSearchPagingFactory().create()

    private val movieSearchPagingSource by lazy {
        MovieSearchPagingSource(
            query = "",
            remoteDataSource = remoteDataSource
        )
    }

    @Test
    fun `must return success load result when load is called`() = runTest {

        //Given
        whenever(remoteDataSource.getSearchMovies(any(), any()))
            .thenReturn(movieSearchPagingFactory)

        //when
        val result = movieSearchPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )

        val resultExpected = listOf(
            movieSearchFactory.create(MovieSearchFactory.Poster.Avengers),
            movieSearchFactory.create(MovieSearchFactory.Poster.JohnWick)
        )

        //then
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
        whenever(remoteDataSource.getSearchMovies(any(), any()))
            .thenThrow(exception)

        //when
        val result = movieSearchPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )

        //then
        assertThat(PagingSource.LoadResult.Error<Int, MovieSearch>(exception)).isEqualTo(result)

    }

}