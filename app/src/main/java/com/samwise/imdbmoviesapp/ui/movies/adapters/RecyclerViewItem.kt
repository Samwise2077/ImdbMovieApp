package com.samwise.imdbmoviesapp.ui.movies.adapters

import com.samwise.imdbmoviesapp.data.ListOfMovies

open class RecyclerViewItem
class SectionItem(val title: String) : RecyclerViewItem()
class MoviesItem(val listOfMovies: ListOfMovies) : RecyclerViewItem()