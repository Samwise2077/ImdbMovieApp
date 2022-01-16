package com.samwise.imdbmoviesapp.di

import com.samwise.imdbmoviesapp.api.ImdbApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideRetrofit() : Retrofit{
       return Retrofit.Builder()
           .baseUrl(ImdbApi.BASE_URL)
           .addConverterFactory(GsonConverterFactory.create())
           .client(OkHttpClient.Builder().build())
           .build()
    }

    @Singleton
    @Provides
    fun provideImdbApi(retrofit: Retrofit) : ImdbApi = retrofit.create(ImdbApi::class.java)


}