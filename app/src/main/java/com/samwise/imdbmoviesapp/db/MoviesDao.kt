package com.samwise.imdbmoviesapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

import com.samwise.imdbmoviesapp.data.ListOfMovies
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {
  @Query("SELECT * FROM list_of_movies WHERE typeOfList = :typeOfList")
  fun getMovies(typeOfList: String): Flow<List<ListOfMovies>>

  @Insert(onConflict = REPLACE)
  suspend fun insertListOfMovies(listOfMovies: ListOfMovies)

  @Query("DELETE FROM list_of_movies WHERE typeOfList = :typeOfList")
  suspend fun deleteMovies(typeOfList: String)

  @Query("DELETE FROM list_of_movies")
  suspend fun deleteAllMovies()
}