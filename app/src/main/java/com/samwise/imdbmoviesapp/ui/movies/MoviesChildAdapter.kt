package com.samwise.imdbmoviesapp.ui.movies

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.samwise.imdbmoviesapp.api.Query
import com.samwise.imdbmoviesapp.data.Movie
import com.samwise.imdbmoviesapp.databinding.GalleryFragmentItemBinding
import com.samwise.imdbmoviesapp.databinding.GalleryFragmentItemTitleBinding

private const val TAG = "MoviesChildAdapter"

class MoviesChildAdapter(private val listener: OnItemClickListener, val typeOfQuery: Query) : ListAdapter<Movie, MoviesChildAdapter.MoviesChildViewHolder>(DiffUtil()) {

    override fun onBindViewHolder(holder: MoviesChildViewHolder, position: Int) {
        Log.d("", "" + this@MoviesChildAdapter)
        Log.d(TAG, "onBindChildViewHolder: child")
        Log.d(TAG, "onBindViewHolder: position - $position")
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
                Log.d(TAG, "bindingPosition: $bindingAdapterPosition")
                if(position != RecyclerView.NO_POSITION){
                    val item = getItem(position)
                    if(item != null){
                        listener.onItemClick(item)
                    }
                }
            }
        }
        @SuppressLint("SetTextI18n")
        fun bind(movie: Movie){
            binding.apply {
                title.text = movie.title
                if(typeOfQuery == Query.COMING_SOON){
                  //  rating.text = movie.releaseState
                    star.isVisible = false
                }
                else if(typeOfQuery == Query.IN_THEATERS){
                    star.isVisible = false
                    rating.text = ""
                }
                else if(movie.imDbRating.isNotEmpty()){
                    Log.d(TAG, "bind: ${movie.imDbRating}")
                    var ratingNumber: Number = movie.imDbRating.toDouble()
                    if(movie.imDbRating.toDouble() == movie.imDbRating.toDouble().toInt().toDouble()){
                        ratingNumber = movie.imDbRating.toDouble().toInt()
                    }
                    rating.text = "$ratingNumber/10"
                }
                Log.d(TAG, "bind: ${rating.text}")
                Glide.with(itemView)
                    .load(movie.image)
                    .centerCrop()
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
