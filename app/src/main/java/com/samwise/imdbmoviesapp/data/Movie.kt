package com.samwise.imdbmoviesapp.data

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlin.random.Random

@TypeConverters(Converters::class)
@Parcelize
data class Movie(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("fullTitle")
    val fullTitle : String,
    @SerializedName("year")
    val year: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("runtimeStr")
    val runtimeStr: String,
    @SerializedName("plot")
    val plot: String,
    @SerializedName("countries")
    val countries: String,
    @SerializedName("contentRating")
    val contentRating: String,
    @SerializedName("imDbRating")
    var imDbRating: String,
    @SerializedName("metacriticRating")
    val metacriticRating: String,
    @SerializedName("genres")
    val genres: String,
    @SerializedName("directors")
    val directors: String,

    @SerializedName("errorMessage")
    val errorMessage: String,


    @Embedded
    @TypeConverters(Converters::class)
    @SerializedName("tvSeriesInfo")
       val tvSeriesInfo: TvSeriesInfo,
    @Embedded
    @TypeConverters(Converters::class)
    @SerializedName(" tvEpisodeInfo")
       var tvEpisodeInfo: TvEpisodeInfo,

    @Embedded
    @TypeConverters(Converters::class)
    @SerializedName("similars")
    val similars: List<SimilarShort>,

    @Embedded
    @TypeConverters(Converters::class)
    @SerializedName("actorList")
    val actorList: List<ActorShort>,
    val starList: List<StarShort>,
    val writerList: List<StarShort>,
    val fullCastData: FullCast

) : Parcelable{


    @Parcelize
    data class TvSeriesInfo(
        @SerializedName("yearEnd")
        val yearEnd: String,
        @SerializedName("creators")
        val creators: String,
        @SerializedName("creatorList")
        val creatorList: List<StarShort>,
        @SerializedName("seasons")
        val seasons: List<String>) : Parcelable


    @Parcelize
    data class StarShort(
        @SerializedName("id")
        val id: String,
        @SerializedName("name")
        val name: String) : Parcelable

    @Parcelize
    data class TvEpisodeInfo(
        @SerializedName("seriesId")
        val seriesId: String,
        @SerializedName("seriesTitle")
        val seriesFullTitle: String,
        @SerializedName("seriesYear")
        val seriesYear: String,
        @SerializedName("seasonNumber")
        val seasonNumber: String,
        @SerializedName("episodeNumber")
        val episodeNumber: String,
        @SerializedName("previousEpisodeId")
        val previousEpisodeId: String,
        @SerializedName("nextEpisodeId")
        val nextEpisodeId: String) : Parcelable

    @Parcelize
    data class SimilarShort(
        @SerializedName("id") val id: String,
        @SerializedName("title") val title: String,
        @SerializedName("image") val image: String,
        @SerializedName("imDbRating") val imDbRating: String) : Parcelable


    override fun hashCode(): Int {
        return super.hashCode()
    }

}