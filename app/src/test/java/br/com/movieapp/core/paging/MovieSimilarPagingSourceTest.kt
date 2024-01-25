package br.com.movieapp.core.paging

import androidx.paging.PagingSource
import br.com.movieapp.TestDispatcherRule
import br.com.movieapp.core.domain.MovieSearch
import br.com.movieapp.core.domain.model.MovieFactory
import br.com.movieapp.core.domain.model.MoviePagingFactory
import br.com.movieapp.movie_details_feature.domain.source.MovieDetailsRemoteDataSource
import com.google.common.truth.Truth
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
class MovieSimilarPagingSourceTest {

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    @Mock
    lateinit var remoteDataSource: MovieDetailsRemoteDataSource

    private val movieFactory = MovieFactory()

    private val moviePagingFactory = MoviePagingFactory().create()

    private val movieSimilarPagingSource by lazy {
        MovieSimilarPagingSource(
            movieId = 1,
            remoteDataSource = remoteDataSource
        )
    }

    @Test
    fun `must return success load result when load is called`() = runTest {

        //Given
        whenever(remoteDataSource.getMoviesSimilar(any(), any())).thenReturn(
            moviePagingFactory
        )

        //when
        val result = movieSimilarPagingSource.load(
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

        //then
        Truth.assertThat(
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
        whenever(remoteDataSource.getMoviesSimilar(any(), any()))
            .thenThrow(exception)

        //when
        val result = movieSimilarPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )

        //then
        Truth.assertThat(PagingSource.LoadResult.Error<Int, MovieSearch>(exception)).isEqualTo(result)

    }

}