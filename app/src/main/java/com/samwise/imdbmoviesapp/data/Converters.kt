package com.samwise.imdbmoviesapp.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.samwise.imdbmoviesapp.api.ImdbResponse
import java.lang.reflect.Type

object Converters {
    @TypeConverter
    fun fromString(value: String): List<String> {
        val listType: Type = object : TypeToken<ArrayList<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: List<String>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromTvEpisodeInfo(source: Movie.TvEpisodeInfo) : String{
        val gson = Gson()
        return gson.toJson(source)
    }

    @TypeConverter
    fun fromTvSeriesInfo(source: Movie.TvSeriesInfo) : String{
        val gson = Gson()
        return gson.toJson(source)
    }

   /* @TypeConverter
    fun fromTvStarShort(source: Movie.StarShort) : String{
        val gson = Gson()
        return gson.toJson(source)
    }*/

    @TypeConverter
    fun fromSimilarShort(source: List<Movie.SimilarShort>) : String{
        val gson = Gson()
        return gson.toJson(source)
    }

    @TypeConverter
    fun fromActorShort(source: List<ActorShort>) : String{
        val gson = Gson()
        return gson.toJson(source)
    }

    @TypeConverter
    fun fromStarShort(source: List<Movie.StarShort>) : String{
        val gson = Gson()
        return gson.toJson(source)
    }

    @TypeConverter
    fun fromFullCast(source: FullCast) : String{
        val gson = Gson()
        return gson.toJson(source)
    }

    @TypeConverter
    fun fromMovie(source: Movie) : String{
        val gson = Gson()
        return gson.toJson(source)
    }

    @TypeConverter
    fun fromResponse(source: ImdbResponse) : String{
        val gson = Gson()
        return gson.toJson(source)
    }

    @TypeConverter
    fun toResponse(source: String) : ImdbResponse{

        return Gson().fromJson(source, ImdbResponse::class.java)
    }

    @TypeConverter
    fun fromMovieList(source: List<Movie>) : String{
        val gson = Gson()
        return gson.toJson(source)
    }

    @TypeConverter
    fun toMovieList(source: String) : List<Movie>{
        return listOf(Gson().fromJson(source, Movie::class.java))
    }

   /* @TypeConverter
    fun fromMovieShort(source: List<MovieShort>) : String{
        val gson = Gson()
        return gson.toJson(source)
    }*/
}