package com.samwise.imdbmoviesapp.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.samwise.imdbmoviesapp.data.Movie
import com.samwise.imdbmoviesapp.databinding.GalleryFragmentItemBinding

class MoviesChildAdapter(val movies: ListOfMovies) : PagingDataAdapter<Movie, MoviesChildAdapter.MoviesChildViewHolder>(DiffUtil()) {

    override fun onBindViewHolder(holder: MoviesChildViewHolder, position: Int) {
         val currentItem = getItem(position)
         if (currentItem != null) {
            holder.bind(movies.listOfMovies[position])
         }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesChildViewHolder {
        val binding = GalleryFragmentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesChildViewHolder(binding)
    }

    class MoviesChildViewHolder(private val binding: GalleryFragmentItemBinding) : RecyclerView.ViewHolder(binding.root){
              init {

              }
              fun bind(movie: Movie){
                 binding.apply {
                       title.text = movie.title
                       rating.text = movie.imDbRatingCount
                 }
              }
    }

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem.id == newItem.id


        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem


    }
}