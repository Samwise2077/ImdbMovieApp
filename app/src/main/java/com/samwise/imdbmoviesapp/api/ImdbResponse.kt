package com.samwise.imdbmoviesapp.api

import android.os.Parcelable
import com.samwise.imdbmoviesapp.data.Movie
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImdbResponse(
    val items: List<Movie>,
    val errorMessage: String
) : Parcelable
