package com.samwise.imdbmoviesapp.ui

import com.samwise.imdbmoviesapp.data.Movie

interface OnItemClickListener{
    fun onMovieClick(movie: Movie)
}