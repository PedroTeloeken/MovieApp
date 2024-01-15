package br.com.movieapp.core.di

import android.content.Context
import androidx.room.Room
import br.com.movieapp.core.data.local.MovieDataBase
import br.com.movieapp.core.data.local.dao.MovieDao
import br.com.movieapp.core.util.Constants.MOVIE_DATA_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideAppDataBase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context = context,
        klass = MovieDataBase::class.java,
        name = MOVIE_DATA_NAME
    ).build()

    @Provides
    @Singleton
    fun provideMovieDao(dataBase: MovieDataBase) : MovieDao{
        return dataBase.movieDao()
    }

}