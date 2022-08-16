package com.samwise.imdbmoviesapp.data

import android.os.Parcelable
import androidx.room.*
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlin.random.Random


@TypeConverters(Converters::class)
@Parcelize
data class Movie(

    var id: String,
    var title: String,
    var fullTitle : String,
    var year: String,
    var image: String,
    var runtimeStr: String,
    var plot: String,
    var countries: String,
    var contentRating: String,
    var imDbRating: String,
    var metacriticRating: String,
    var genres: String,
    var directors: String,

    var errorMessage: String,



    @Embedded
    @TypeConverters(Converters::class)
    var tvSeriesInfo: TvSeriesInfo,

    @Embedded
    @TypeConverters(Converters::class)
       var tvEpisodeInfo: TvEpisodeInfo,

    @Embedded
    @TypeConverters(Converters::class)
    var similars: List<SimilarShort>,

    @Embedded
    @TypeConverters(Converters::class)
    var actorList: List<ActorShort>,

    @Embedded
    @TypeConverters(Converters::class)
    var starList: List<StarShort>,

    @Embedded
    @TypeConverters(Converters::class)
    var writerList: List<StarShort>,

    @Embedded
    @TypeConverters(Converters::class)
    var fullCastData: FullCast

) : Parcelable{
    constructor() : this("", "", "", "",
        "", "", "", "", ", ", "", "",
        "", "", "",
        TvSeriesInfo("", "", listOf(StarShort("", "")), listOf("")),
        TvEpisodeInfo("", "", "", "", "", "",""),
        listOf(SimilarShort("", "", "", "")),
        listOf(ActorShort("", "", "", "")),
        listOf(StarShort("", "")),
        listOf(StarShort("", "")),
        FullCast("", listOf(ActorShort("", "", "", "")), listOf(CastShort("", listOf(CastShortItem("", "", "")))),
            CastShort("", listOf(CastShortItem("", "", ""))), CastShort("", listOf(CastShortItem("", "", ""))))
    )

    @Parcelize
    data class TvSeriesInfo(

        val yearEnd: String,

        val creators: String,
        @Embedded

        @TypeConverters(Converters::class)
        val creatorList: List<StarShort>,

        val seasons: List<String>) : Parcelable{

        }


    @Parcelize
    data class StarShort(
        val id: String,
        val name: String) : Parcelable{

        }

    @Parcelize
    data class TvEpisodeInfo(
        val seriesId: String,
        val seriesFullTitle: String,
        val seriesYear: String,
        val seasonNumber: String,
        val episodeNumber: String,
        val previousEpisodeId: String,
        val nextEpisodeId: String) : Parcelable{

        }

    @Parcelize
    data class SimilarShort(
        val id: String,
         val title: String,
         val image: String,
         val imDbRating: String) : Parcelable{

        }


    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Movie

        if (id != other.id) return false
        if (title != other.title) return false
        if (fullTitle != other.fullTitle) return false
        if (year != other.year) return false
        if (image != other.image) return false
        if (runtimeStr != other.runtimeStr) return false
        if (plot != other.plot) return false
        if (countries != other.countries) return false
        if (contentRating != other.contentRating) return false
        if (imDbRating != other.imDbRating) return false
        if (metacriticRating != other.metacriticRating) return false
        if (genres != other.genres) return false
        if (directors != other.directors) return false
        if (errorMessage != other.errorMessage) return false
        if (tvSeriesInfo != other.tvSeriesInfo) return false
        if (tvEpisodeInfo != other.tvEpisodeInfo) return false
        if (similars != other.similars) return false
        if (actorList != other.actorList) return false
        if (starList != other.starList) return false
        if (writerList != other.writerList) return false
        if (fullCastData != other.fullCastData) return false

        return true
    }

}