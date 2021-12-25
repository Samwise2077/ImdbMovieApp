package com.samwise.imdbmoviesapp.ui.movies

import android.os.Parcelable
import com.samwise.imdbmoviesapp.api.Query
import com.samwise.imdbmoviesapp.data.Movie
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListOfMovies(val listOfMovies: List<Movie>, val typeOfList: Query) : Parcelable