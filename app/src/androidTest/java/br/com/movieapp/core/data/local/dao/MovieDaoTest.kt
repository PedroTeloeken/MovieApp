package br.com.movieapp.core.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import br.com.movieapp.core.data.local.MovieDataBase
import br.com.movieapp.core.data.local.entity.MovieEntity
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
@SmallTest
class MovieDaoTest {

    @get:Rule
    val hiltRules = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var dataBase: MovieDataBase
    private lateinit var movieDao: MovieDao

    @Before
    fun setup() {
        hiltRules.inject()
        movieDao = dataBase.movieDao()
    }

    @After
    fun tearDown() {
        dataBase.close()
    }

    @Test
    fun test_getMovies_should_return_list_of_movies() = runTest {

        //Given - Nothing

        //When
        val movies = movieDao.getMovies().first()

        assertThat(movies.size).isEqualTo(0)

    }

    @Test
    fun test_getMovies_should_return_movies_ordered_by_id() = runTest {

        //Given
        val moviesEntity = listOf(
            MovieEntity(movieId = 4, title = "Homem de ferro 4", imageUrl = "Url4"),
            MovieEntity(movieId = 1, title = "Homem de ferro 1", imageUrl = "Url1"),
            MovieEntity(movieId = 3, title = "Homem de ferro 3", imageUrl = "Url3"),
            MovieEntity(movieId = 2, title = "Homem de ferro 2", imageUrl = "Url2"),
        )
        movieDao.insertMovies(moviesEntity)

        //When
        val movies = movieDao.getMovies().first()

        //Then
        assertThat(movies.size).isEqualTo(4)
        assertThat(movies[0].movieId).isEqualTo(1)
        assertThat(movies[1].movieId).isEqualTo(2)
        assertThat(movies[2].movieId).isEqualTo(3)
        assertThat(movies[3].movieId).isEqualTo(4)

    }

    @Test
    fun test_movie_should_return_correct_movie_by_id() = runTest {

        //Given
        val movieEntity = MovieEntity(movieId = 4, title = "Homem de ferro 4", imageUrl = "Url4")
        movieDao.insertMovie(movieEntity)
        val movies = movieDao.getMovies().first()
        val movieClick = movies[0]

        //When
        val movieId = movieDao.getMovie(movieClick.movieId)

        //Then
        assertThat(movieId?.title).isEqualTo("Homem de ferro 4")

    }

    @Test
    fun test_insertMovies_should_insert_movies_successfully() = runTest {

        //Given
        val moviesEntity = listOf(
            MovieEntity(movieId = 4, title = "Homem de ferro 4", imageUrl = "Url4"),
            MovieEntity(movieId = 1, title = "Homem de ferro 1", imageUrl = "Url1"),
            MovieEntity(movieId = 3, title = "Homem de ferro 3", imageUrl = "Url3"),
            MovieEntity(movieId = 2, title = "Homem de ferro 2", imageUrl = "Url2"),
        )

        //when
        movieDao.insertMovies(moviesEntity)


        //Then
        val insertMovies = movieDao.getMovies().first()
        assertThat(moviesEntity.size).isEqualTo(insertMovies.size)
        assertThat(insertMovies.containsAll(moviesEntity))

    }

    @Test
    fun test_insertMovie_should_insert_a_movie_successfully() = runTest {

        //Given
        val movieEntity = MovieEntity(movieId = 4, title = "Homem de ferro 4", imageUrl = "Url4")

        //When
        movieDao.insertMovie(movieEntity = movieEntity)

        val movies = movieDao.getMovies().first()
        assertThat(movies[0].title).isEqualTo(movieEntity.title)

    }

    @Test
    fun test_IsFavorite_should_return_favorite_movie_when_movie_marked_as_favorite() = runTest {

        //Given
        val movieId = 5321
        val favoriteMovie = MovieEntity(movieId = movieId, title = "Avengers", imageUrl = "URl")
        movieDao.insertMovie(favoriteMovie)

        //when
        val result = movieDao.isFavorite(movieId)

        //Then
        assertThat(result).isEqualTo(favoriteMovie)
    }

    @Test
    fun test_isFavorite_should_return_null_when_movie_is_not_marked_as_favorite() = runTest {

        //Given
        val movieId = 5321

        //when
        val result = movieDao.isFavorite(movieId = movieId)

        //Then
        assertThat(result).isEqualTo(null)

    }

    @Test
    fun test_updateMovie_should_update_a_movie_successfully() = runTest {
        //Given
        val movieEntity = MovieEntity(movieId = 4, title = "Homem de ferro 4", imageUrl = "Url4")
        movieDao.insertMovie(movieEntity)
        val allMovie = movieDao.getMovies().first()
        val updateMovie = allMovie[0].copy(title = "Homem aranha")

        //when
        movieDao.insertMovie(updateMovie)

        //then
        val movies = movieDao.getMovies().first()
        assertThat(movies[0].title).contains(updateMovie.title)

    }
    @Test
    fun test_deleteMovie_should_delete_a_movie_successfully() = runTest {

        //given
        val movieEntity = MovieEntity(movieId = 4, title = "Homem de ferro 4", imageUrl = "Url4")
        movieDao.insertMovie(movieEntity)

        //When
        movieDao.deleteMovie(movieEntity)

        //then
        val movies = movieDao.getMovies().first()
        assertThat(movies).isEmpty()

    }
}