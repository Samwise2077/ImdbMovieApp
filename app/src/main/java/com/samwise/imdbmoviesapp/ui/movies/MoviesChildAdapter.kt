package com.samwise.imdbmoviesapp.ui.movies

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.samwise.imdbmoviesapp.data.Movie
import com.samwise.imdbmoviesapp.databinding.GalleryFragmentItemBinding

private const val TAG = "MoviesChildAdapter"

class MoviesChildAdapter(private val listener: OnItemClickListener) : ListAdapter<Movie, MoviesChildAdapter.MoviesChildViewHolder>(DiffUtil()) {

    override fun onBindViewHolder(holder: MoviesChildViewHolder, position: Int) {
        Log.d(TAG, "onBindChildViewHolder: child")
         val currentItem = getItem(position)
         if (currentItem != null) {
            holder.bind(currentItem)
         }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesChildViewHolder {
        Log.d(TAG, "onCreateChildViewHolder:  child")
        val binding = GalleryFragmentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesChildViewHolder(binding)
    }

    inner class MoviesChildViewHolder(private val binding: GalleryFragmentItemBinding) : RecyclerView.ViewHolder(binding.root){
              init {
                  binding.root.setOnClickListener {
                      val position = bindingAdapterPosition
                      if(position != RecyclerView.NO_POSITION){
                          val item = getItem(position)
                          if(item != null){
                             listener.onItemClick(item)
                          }
                      }
                  }
              }
              fun bind(movie: Movie){
                 binding.apply {
                       title.text = movie.title
                       rating.text = movie.imDbRatingCount
                       Glide.with(itemView)
                           .load(movie.image)
                           .transition(DrawableTransitionOptions.withCrossFade())
                           .into(poster)
                 }
              }
    }

    interface OnItemClickListener{
       fun onItemClick(movie: Movie)
    }

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem
    }
}