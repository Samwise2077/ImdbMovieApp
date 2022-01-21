package com.samwise.imdbmoviesapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.samwise.imdbmoviesapp.api.ImdbApi
import com.samwise.imdbmoviesapp.api.Query
import com.samwise.imdbmoviesapp.ui.movies.ListOfMovies
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


private const val TAG = "MoviesRepository"

@Singleton
class MoviesRepository @Inject constructor(
    private val imdbApi: ImdbApi) {

    fun getMoviesComingSoon() : LiveData<PagingData<ListOfMovies>> {
        Log.d(TAG, "getMoviesComingSoon: fuck youuuuu")
        return Pager(
            config = PagingConfig(
               pageSize = 1,
               maxSize = 5,
               enablePlaceholders = false
            ),
            pagingSourceFactory ={MoviesPagingSource(imdbApi, Query.COMING_SOON)}
        ).liveData
    }

   /* fun getAllLists() : PagingData<ListOfMovies>{
        val pagingData =
    }*/


}