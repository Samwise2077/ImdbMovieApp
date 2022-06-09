package com.samwise.imdbmoviesapp.api

import retrofit2.http.GET
import retrofit2.http.Query

interface ImdbApi {

    //Samwise 1 - "k_pwxueno2"
    //Samwise 2 - "k_plyw0b4y"
    //Samwise 3 - "k_i2hks2dd"


    companion object{
        const val CLIENT_ID =  "k_plyw0b4y"
        const val BASE_URL = "https://imdb-api.com/"
    }

 //   @Headers("X-Api-Key: ${CLIENT_ID}")
    @GET("API/ComingSoon")
    suspend fun searchMoviesComingSoon(
     @Query("lang") language: String = "en",
     @Query("apiKey") apiKey: String = CLIENT_ID
    ) : ImdbResponse

    @GET("API/MostPopularMovies")
    suspend fun searchMostPopularMovies(
        @Query("lang") language: String = "en",
        @Query("apiKey") apiKey: String = CLIENT_ID
    ) : ImdbResponse

    @GET("API/Top250Movies")
    suspend fun searchTop250Movies(
        @Query("lang") language: String = "en",
        @Query("apiKey") apiKey: String = CLIENT_ID
    ) : ImdbResponse

    @GET("API/Top250TVs")
    suspend fun searchTop250Tvs(
        @Query("lang") language: String = "en",
        @Query("apiKey") apiKey: String = CLIENT_ID
    ) : ImdbResponse

    @GET("API/InTheaters")
    suspend fun searchMoviesInTheaters(
        @Query("lang") language: String = "en",
        @Query("apiKey") apiKey: String = CLIENT_ID
    ) : ImdbResponse
}

enum class Query{
   COMING_SOON, IN_THEATERS, MostPopularMovies, Top250Movies, Top250TVs
}