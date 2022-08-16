package com.samwise.imdbmoviesapp.data

import androidx.room.withTransaction
import com.samwise.imdbmoviesapp.api.ImdbApi
import com.samwise.imdbmoviesapp.api.ImdbResponse
import com.samwise.imdbmoviesapp.api.Query
import com.samwise.imdbmoviesapp.db.MoviesDatabase
//import com.samwise.imdbmoviesapp.db.MoviesDatabase
import com.samwise.imdbmoviesapp.util.networkBoundResource
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton


private const val TAG = "MoviesRepository"

@Singleton
class MoviesRepository @Inject constructor(
    private val imdbApi: ImdbApi,
    private val database: MoviesDatabase
) {
    private val moviesDao = database.moviesDao()

    suspend fun getMoviesComingSoon(): List<Movie> {
        return imdbApi.searchMoviesComingSoon().items
    }

    suspend fun getMostPopularMovies(): List<Movie> {
        return imdbApi.searchMostPopularMovies().items
    }

    fun getM() = networkBoundResource(
        query = {
            moviesDao.getMovies(Query.MOST_POPULAR_MOVIES.name)
        },
        fetch = {
            delay(2000)
            imdbApi.searchMostPopularMovies()
        },
        saveFetchResult = { movies ->
            database.withTransaction {
                moviesDao.deleteMovies(Query.MOST_POPULAR_MOVIES.name)
                moviesDao.insertListOfMovies(ListOfMovies(Query.MOST_POPULAR_MOVIES, ImdbResponse( movies.items, "")))
            }
        }
    )

    suspend fun getMoviesInTheaters(): List<Movie> {
        return imdbApi.searchMoviesInTheaters().items
    }

    suspend fun getTop250Movies(): List<Movie> {
        return imdbApi.searchTop250Movies().items
    }

    suspend fun getTop250Tvs(): List<Movie> {
        return imdbApi.searchTop250Tvs().items
    }

    suspend fun getMovie(id: String): Movie {
        return imdbApi.searchMovie(id = id)
    }

    suspend fun getReviews(id: String) : Reviews{
        return imdbApi.searchReviews(id = id)
    }

}