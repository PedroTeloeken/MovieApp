package br.com.movieapp.movie_details_feature.presentacion

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.paging.PagingData
import br.com.movieapp.TestDispatcherRule
import br.com.movieapp.core.domain.model.MovieDetailsFactory
import br.com.movieapp.core.domain.model.MovieFactory
import br.com.movieapp.core.util.ResultData
import br.com.movieapp.movie_details_feature.domain.usecase.GetMovieDetailsUseCase
import br.com.movieapp.movie_favorite_screen.domain.usecase.AddMovieFavoriteUseCase
import br.com.movieapp.movie_favorite_screen.domain.usecase.DeleteMovieFavoriteUseCase
import br.com.movieapp.movie_favorite_screen.domain.usecase.IsFavoriteMoviesUseCase
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieDetailViewModelTest {

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    @Mock
    lateinit var getMovieDetailUseCase: GetMovieDetailsUseCase

    @Mock
    lateinit var addMovieDetailsUseCase: AddMovieFavoriteUseCase

    @Mock
    lateinit var deleteMovieDetailsUseCase: DeleteMovieFavoriteUseCase

    @Mock
    lateinit var isMovieFavoriteUseCase: IsFavoriteMoviesUseCase

    @Mock
    lateinit var savedStateHandle: SavedStateHandle

    private val movieDetailsFactory =
        MovieDetailsFactory().create(poster = MovieDetailsFactory.Poster.Avengers)

    private val pagingData = PagingData.from(
        listOf(
            MovieFactory().create(poster = MovieFactory.Poster.Avengers),
            MovieFactory().create(poster = MovieFactory.Poster.JohnWick)
        )
    )

    private val movie = MovieFactory().create(poster = MovieFactory.Poster.Avengers)

    private val viewModel by lazy {
        MovieDetailViewModel(
            getMovieDetailsUseCase = getMovieDetailUseCase,
            addMovieFavoriteUseCase = addMovieDetailsUseCase,
            deleteMovieFavoriteUseCase = deleteMovieDetailsUseCase,
            isMovieFavoriteUseCase = isMovieFavoriteUseCase,
            saveStateHandle = savedStateHandle.apply {
                whenever(savedStateHandle.get<Int>("movieId")).thenReturn(movie.id)
            }

        )
    }

    @Test
    fun `must notify uiState with success when get movies similar and movie details returns success`() =
        runTest {

            //Given
            whenever(getMovieDetailUseCase.invoke(any()))
                .thenReturn(ResultData.Success(flowOf(pagingData) to movieDetailsFactory))

            whenever(isMovieFavoriteUseCase.invoke(any())).thenReturn(flowOf(ResultData.Success(true)))

            val argumentCaptor = argumentCaptor<GetMovieDetailsUseCase.Params>()

            val checkArgumentCaptor = argumentCaptor<IsFavoriteMoviesUseCase.Params>()

            //when
            viewModel.uiState.isLoading

            //then
            verify(isMovieFavoriteUseCase).invoke(checkArgumentCaptor.capture())
            assertThat(movieDetailsFactory.id).isEqualTo(argumentCaptor.firstValue.movieId)

            verify(getMovieDetailUseCase).invoke(argumentCaptor.capture())
            assertThat(movieDetailsFactory.id).isEqualTo(argumentCaptor.firstValue.movieId)

            val movieDetails = viewModel.uiState.movieDetails
            val results = viewModel.uiState.results

            assertThat(movieDetails).isNotNull()
            assertThat(results).isNotNull()

        }

    @Test
    fun `must notify with failure when get movie details and returns exception`() = runTest {

        //Given
        val exception = Exception("Um erro ocorreu")
        whenever(getMovieDetailUseCase.invoke(any())).thenReturn(ResultData.Failure(exception))

        whenever(isMovieFavoriteUseCase.invoke(any())).thenReturn(flowOf(ResultData.Failure(exception)))


        //when
        viewModel.uiState.isLoading

        //then
        val error = viewModel.uiState.error
        assertThat(exception.message).isEqualTo(error)

    }

    @Test
    fun `must call delete favorite and notify of uiState with filled favorite icon when current icon is checked`() =
        runTest {

            //Given
            whenever(addMovieDetailsUseCase.invoke(any())).thenReturn(
                flowOf(ResultData.Success(Unit))
            )

            whenever(isMovieFavoriteUseCase.invoke(any())).thenReturn(
                flowOf(ResultData.Success(false))
            )

            val deleteArgumentCaptor = argumentCaptor<DeleteMovieFavoriteUseCase.Params>()
            val checkedArgumentCaptor = argumentCaptor<IsFavoriteMoviesUseCase.Params>()

            //when
            viewModel.onAddFavorite(movie)

            //then
            verify(deleteMovieDetailsUseCase).invoke(deleteArgumentCaptor.capture())

            assertThat(movie).isEqualTo(deleteArgumentCaptor.firstValue.movie)

            verify(isMovieFavoriteUseCase).invoke(checkedArgumentCaptor.capture())
            assertThat(movie.id).isEqualTo(checkedArgumentCaptor.firstValue.movieId)

            val iconColor = viewModel.uiState.iconColor

            assertThat(Color.White).isEqualTo(iconColor)

        }

    @Test
    fun `must notify uiState with filled favorite icon when current icon is unchecked`() =
        runTest {

            //Given
            whenever(deleteMovieDetailsUseCase.invoke(any())).thenReturn(
                flowOf(ResultData.Success(Unit))
            )

            whenever(isMovieFavoriteUseCase.invoke(any())).thenReturn(
                flowOf(ResultData.Success(true))
            )

            val deleteArgumentCaptor = argumentCaptor<DeleteMovieFavoriteUseCase.Params>()
            val checkedArgumentCaptor = argumentCaptor<IsFavoriteMoviesUseCase.Params>()

            //when
            viewModel.onAddFavorite(movie)

            //then
            verify(deleteMovieDetailsUseCase).invoke(deleteArgumentCaptor.capture())
            assertThat(movie).isEqualTo(deleteArgumentCaptor.firstValue.movie)

            verify(isMovieFavoriteUseCase).invoke(checkedArgumentCaptor.capture())
            assertThat(movie.id).isEqualTo(checkedArgumentCaptor.firstValue.movieId)

            val iconColor = viewModel.uiState.iconColor
            assertThat(Color.Red).isEqualTo(iconColor)

        }

    @Test
    fun `must notify uiState with bookmark icon filled in if bookmark check returns true`() =
        runTest {

            //Given
            whenever(isMovieFavoriteUseCase.invoke(any()))
                .thenReturn(flowOf(ResultData.Success(true)))

            val checkedArgumentCaptor = argumentCaptor<IsFavoriteMoviesUseCase.Params>()

            //when
            viewModel.uiState.isLoading

            verify(isMovieFavoriteUseCase).invoke(checkedArgumentCaptor.capture())

            assertThat(movie.id).isEqualTo(checkedArgumentCaptor.firstValue.movieId)

            val iconColor = viewModel.uiState.iconColor
            assertThat(Color.Red).isEqualTo(iconColor)

        }

    @Test
    fun `must notify uiState with bookmark icon filled in if bookmark check returns false`() =
        runTest {

            //Given
            whenever(isMovieFavoriteUseCase.invoke(any()))
                .thenReturn(flowOf(ResultData.Success(false)))

            val checkedArgumentCaptor = argumentCaptor<IsFavoriteMoviesUseCase.Params>()

            //when
            viewModel.uiState.isLoading

            verify(isMovieFavoriteUseCase).invoke(checkedArgumentCaptor.capture())

            assertThat(movie.id).isEqualTo(checkedArgumentCaptor.firstValue.movieId)

            val iconColor = viewModel.uiState.iconColor
            assertThat(Color.White).isEqualTo(iconColor)

        }

}