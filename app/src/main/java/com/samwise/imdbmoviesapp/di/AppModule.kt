package com.samwise.imdbmoviesapp.di

import android.app.Application
import androidx.room.Room
import com.samwise.imdbmoviesapp.api.ImdbApi
import com.samwise.imdbmoviesapp.db.MoviesDatabase
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

    @Singleton
    @Provides
    fun provideDatabase(app: Application) : MoviesDatabase =
        Room.databaseBuilder(app, MoviesDatabase::class.java, "movies_database")
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideDao(database: MoviesDatabase) = database.moviesDao()


}