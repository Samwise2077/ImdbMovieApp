package com.samwise.imdbmoviesapp.data

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


@Singleton
class MoviesRepository @Inject constructor(
    private val imdbApi: ImdbApi) {

    fun getMoviesComingSoon() : PagingData<Movie> {
        return Pager(
            config = PagingConfig(
               pageSize = 20,
               maxSize = 100,
               enablePlaceholders = false
            ),
            pagingSourceFactory ={MoviesPagingSource(imdbApi, Query.COMING_SOON)}

        )
    }

    fun getAllLists() : PagingData<ListOfMovies>{
        val pagingData =
    }


}