package com.samwise.imdbmoviesapp.api

import com.samwise.imdbmoviesapp.data.Movie

data class ImdbResponse(
    val items: List<Movie>,
    val errorMessage: String
)
