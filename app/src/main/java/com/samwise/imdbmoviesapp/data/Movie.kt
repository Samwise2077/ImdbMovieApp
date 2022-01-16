package com.samwise.imdbmoviesapp.data

import android.os.Parcelable
import androidx.room.TypeConverters
import kotlinx.parcelize.Parcelize

@TypeConverters(Converters::class)
@Parcelize
data class Movie(
    val id: String,
    val title: String,
    val year: String,
    val image: String,
    val runtimeStr: String,
    val plot: String,
    val contentRating: String,
    val imDbRating: String,
    val imDbRatingCount: String,
    val metacriticRating: String,
  //  val genreList: Map<String, String>,
  //  val directorList: Map<String, String>,
  //  val starList : Map<String, String>,
) : Parcelable