package br.com.movieapp.movie_popular_feature.di

import br.com.movieapp.core.data.remote.service.MovieService
import br.com.movieapp.movie_popular_feature.data.repository.MoviePopularRepositoryImpl
import br.com.movieapp.movie_popular_feature.data.source.MoviePopularRemoteDataSourceImpl
import br.com.movieapp.movie_popular_feature.domain.repository.MoviePopularRepository
import br.com.movieapp.movie_popular_feature.domain.source.MoviePopularRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MoviePopularFeatureModule {

    @Singleton
    @Provides
    fun providesDataSource(service: MovieService): MoviePopularRemoteDataSource {
        return MoviePopularRemoteDataSourceImpl(service = service)
    }

    @Singleton
    @Provides
    fun provideMovieRepository(remoteDataSource: MoviePopularRemoteDataSource): MoviePopularRepository {
        return MoviePopularRepositoryImpl(remoteDataSource = remoteDataSource)
    }

}