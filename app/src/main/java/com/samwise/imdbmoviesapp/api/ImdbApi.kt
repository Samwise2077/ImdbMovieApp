package com.samwise.imdbmoviesapp.api

import com.samwise.imdbmoviesapp.data.FullCast
import com.samwise.imdbmoviesapp.data.Movie
import com.samwise.imdbmoviesapp.data.Reviews
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ImdbApi {

    //Samwise 1 - "k_pwxueno2"
    //Samwise 2 - "k_plyw0b4y"
    //Samwise 3 - "k_i2hks2dd"


    companion object{
        const val CLIENT_ID =  "k_plyw0b4y"
        const val BASE_URL = "https://imdb-api.com/API/"
    }

    @GET("ComingSoon")
    suspend fun searchMoviesComingSoon(
     @Query("lang") language: String = "en",
     @Query("apiKey") apiKey: String = CLIENT_ID
    ) : ImdbResponse

    @GET("MostPopularMovies")
    suspend fun searchMostPopularMovies(
        @Query("lang") language: String = "en",
        @Query("apiKey") apiKey: String = CLIENT_ID
    ) : ImdbResponse

    @GET("Top250Movies")
    suspend fun searchTop250Movies(
        @Query("lang") language: String = "en",
        @Query("apiKey") apiKey: String = CLIENT_ID
    ) : ImdbResponse

    @GET("Top250TVs")
    suspend fun searchTop250Tvs(
        @Query("lang") language: String = "en",
        @Query("apiKey") apiKey: String = CLIENT_ID
    ) : ImdbResponse

    @GET("InTheaters")
    suspend fun searchMoviesInTheaters(
        @Query("lang") language: String = "en",
        @Query("apiKey") apiKey: String = CLIENT_ID
    ) : ImdbResponse

    @GET("Title/{apiKey}/{id}")
    suspend fun searchMovie(
        @Path("apiKey") apiKey: String = CLIENT_ID,
        @Path("id") id: String
    ): Movie

    @GET("SearchMovie/{apiKey}/{expression}")
    suspend fun searchSimilars(
        @Path("apiKey") apiKey: String = CLIENT_ID,
        @Path("expression") expression: String
    ) : ImdbSimilarResponse

    @GET("Reviews/{apiKey}/{id}")
    suspend fun searchReviews(
        @Path("apiKey") apiKey: String = CLIENT_ID,
        @Path("id") id: String
    ) : Reviews

    @GET("FullCast/{apiKey}/{id}")
    suspend fun searchFullCast(
        @Path("apiKey") apiKey: String = CLIENT_ID,
        @Path("id") id: String
    ) : FullCast
}

enum class Query{
   COMING_SOON, IN_THEATERS, MOST_POPULAR_MOVIES, TOP_250_MOVIES, TOP_250_TVs, MOVIE, SIMILARS
}