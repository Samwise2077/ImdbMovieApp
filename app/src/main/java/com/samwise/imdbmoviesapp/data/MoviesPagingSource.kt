package com.samwise.imdbmoviesapp.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.samwise.imdbmoviesapp.api.ImdbApi
import com.samwise.imdbmoviesapp.api.Query
import com.samwise.imdbmoviesapp.ui.movies.ListOfMovies
import retrofit2.HttpException
import java.io.IOException

const val MOVIE_STARTING_PAGE_INDEX = 1

private const val TAG = "MoviesPagingSource"

class MoviesPagingSource(
    private val imdbApi: ImdbApi,
    private val typeOfQuery: Query,
    val query: String = ""
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: MOVIE_STARTING_PAGE_INDEX
        return try {
           /* val response =
                when(typeOfQuery){
                    Query.COMING_SOON ->
                        listOf(ListOfMovies(imdbApi.searchMoviesComingSoon("en").movies, Query.COMING_SOON))
                    Query.ALL_LISTS -> TODO("later")
                }*/

           val movies = imdbApi.searchMoviesComingSoon("en").items
            Log.d(TAG, "load: ${movies}")




           // val movies = response.
            LoadResult.Page(

                data = movies,
                prevKey = if(position == MOVIE_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if(movies.isEmpty()) null else position + 1
            )
        }
        catch (exception: IOException){
            Log.e(TAG, "load: ", exception)
           LoadResult.Error(exception)
        }
        catch (exception: HttpException){
            Log.e(TAG, "load: ", exception)
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        TODO("Not yet implemented")
    }
}