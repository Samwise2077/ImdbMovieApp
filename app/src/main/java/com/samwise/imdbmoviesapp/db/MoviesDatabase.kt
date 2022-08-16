package com.samwise.imdbmoviesapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.samwise.imdbmoviesapp.data.ListOfMovies

@Database(entities = [ListOfMovies::class], version = 1)
abstract class MoviesDatabase : RoomDatabase(){
    abstract fun moviesDao() : MoviesDao
}