package com.samwise.imdbmoviesapp.ui.movies

open class RecyclerViewItem
class SectionItem(val title: String) : RecyclerViewItem()
class MoviesItem(val listOfMovies: ListOfMovies) : RecyclerViewItem()