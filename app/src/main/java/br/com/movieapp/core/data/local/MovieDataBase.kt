package br.com.movieapp.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.movieapp.core.data.local.dao.MovieDao
import br.com.movieapp.core.data.local.entity.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 1,
    exportSchema = false
)

abstract class MovieDataBase : RoomDatabase() {

    abstract fun movieDao() : MovieDao

}