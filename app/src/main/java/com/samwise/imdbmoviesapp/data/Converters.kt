package com.samwise.imdbmoviesapp.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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

    @TypeConverter
    fun fromTvStarShort(source: Movie.StarShort) : String{
        val gson = Gson()
        return gson.toJson(source)
    }

    @TypeConverter
    fun fromSimilarShort(source: Movie.SimilarShort) : String{
        val gson = Gson()
        return gson.toJson(source)
    }

    @TypeConverter
    fun fromActorShort(source: ActorShort) : String{
        val gson = Gson()
        return gson.toJson(source)
    }
}