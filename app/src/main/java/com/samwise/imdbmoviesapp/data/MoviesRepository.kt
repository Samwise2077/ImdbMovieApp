package com.samwise.imdbmoviesapp.data

import com.samwise.imdbmoviesapp.api.ImdbApi
import com.samwise.imdbmoviesapp.api.ImdbSimilarResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import javax.inject.Inject
import javax.inject.Singleton


private const val TAG = "MoviesRepository"

@Singleton
class MoviesRepository @Inject constructor(
    private val imdbApi: ImdbApi) {

    suspend fun getMoviesComingSoon(): List<Movie> {
        return imdbApi.searchMoviesComingSoon().items
    }

    suspend fun getMostPopularMovies(): List<Movie> {
        return imdbApi.searchMostPopularMovies().items
    }

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