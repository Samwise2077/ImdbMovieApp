package com.samwise.imdbmoviesapp.api

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ImdbApi {

    companion object{
        const val CLIENT_ID = "k_pwxueno2"
        const val BASE_URL = "https://imdb-api.com/"
    }

    @Headers("X-Api-Key: ${CLIENT_ID}")
    @GET("ComingSoon")
    fun searchMoviesComingSoon(
        @Query("lang") language: String
    ) : ImdbResponse


}

enum class Query{
   ALL_LISTS, COMING_SOON
}