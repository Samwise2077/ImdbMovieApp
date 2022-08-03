package com.samwise.imdbmoviesapp.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FullCast(
    val title: String,
    val actors: List<ActorShort>,
    val others: List<CastShort>,
    val directors: CastShort,
    val writers: CastShort
) : Parcelable

@Parcelize
data class CastShort(
    val job: String,
    val items: List<CastShortItem>
) : Parcelable

@Parcelize
data class CastShortItem (
  val id: String,
  val name: String,
  val description: String
) : Parcelable

@Parcelize
data class ActorShort(
    @SerializedName("id")
    val id: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("asCharacter")
    val asCharacter: String
) : Parcelable
