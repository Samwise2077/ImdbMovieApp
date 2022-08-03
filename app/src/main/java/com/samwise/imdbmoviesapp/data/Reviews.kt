package com.samwise.imdbmoviesapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Reviews(
    val imDbId: String,
    val title: String,
    val fullTitle: String,
    val type: String,
    val year: String,
    val items: List<ReviewDetail>,
    val errorMessage: String) : Parcelable {
}

@Parcelize
data class ReviewDetail(
    val username: String,
    val userUrl: String,
    val reviewLink: String,
    val warningSpoilers: Boolean,
    val date: String,
    val rate: String,
    val helpful: String,
    val title: String,
    val content: String
) : Parcelable
