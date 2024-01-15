package br.com.movieapp.movie_favorite_screen.di

import br.com.movieapp.core.data.local.dao.MovieDao
import br.com.movieapp.movie_favorite_screen.data.repository.MovieFavoriteRepositoryImpl
import br.com.movieapp.movie_favorite_screen.data.source.MovieFavoriteLocalDataSourceImpl
import br.com.movieapp.movie_favorite_screen.domain.repository.MovieFavoriteRepository
import br.com.movieapp.movie_favorite_screen.domain.source.MovieFavoriteLocalDataSource
import br.com.movieapp.movie_favorite_screen.domain.usecase.AddMovieFavoriteUseCase
import br.com.movieapp.movie_favorite_screen.domain.usecase.AddMovieFavoriteUseCaseImpl
import br.com.movieapp.movie_favorite_screen.domain.usecase.DeleteMovieFavoriteUseCase
import br.com.movieapp.movie_favorite_screen.domain.usecase.DeleteMovieFavoriteUseCaseImpl
import br.com.movieapp.movie_favorite_screen.domain.usecase.IsFavoriteMoviesUseCase
import br.com.movieapp.movie_favorite_screen.domain.usecase.IsFavoriteMoviesUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieFavoriteModule {

    @Provides
    @Singleton
    fun providesMovieFavoriteLocalDataSource(dao: MovieDao): MovieFavoriteLocalDataSource {
        return MovieFavoriteLocalDataSourceImpl(dao = dao)
    }

    @Provides
    @Singleton
    fun provideMovieFavoriteRepository(localDataSource: MovieFavoriteLocalDataSource): MovieFavoriteRepository {
        return MovieFavoriteRepositoryImpl(localDataSource = localDataSource)
    }

    @Provides
    @Singleton
    fun provideAddMovieFavoriteUseCase(movieFavoriteRepository: MovieFavoriteRepository): AddMovieFavoriteUseCase {
        return AddMovieFavoriteUseCaseImpl(movieFavoriteRepository = movieFavoriteRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteMovieFavoriteUseCase(movieFavoriteRepository: MovieFavoriteRepository): DeleteMovieFavoriteUseCase {
        return DeleteMovieFavoriteUseCaseImpl(movieFavoriteRepository = movieFavoriteRepository)
    }

    @Provides
    @Singleton
    fun provideIsFavoriteMovieUseCase(movieFavoriteRepository: MovieFavoriteRepository): IsFavoriteMoviesUseCase {
        return IsFavoriteMoviesUseCaseImpl(movieFavoriteRepository = movieFavoriteRepository)
    }
}