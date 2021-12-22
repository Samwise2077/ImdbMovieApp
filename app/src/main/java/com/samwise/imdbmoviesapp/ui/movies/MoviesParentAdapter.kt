package com.samwise.imdbmoviesapp.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.samwise.imdbmoviesapp.databinding.ItemParentBinding

class MoviesParentAdapter : ListAdapter<ListOfMovies, MoviesParentAdapter.MoviesParentViewHolder>(DiffUtil()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesParentViewHolder {
        val binding = ItemParentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesParentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesParentViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }


    class MoviesParentViewHolder(private val binding: ItemParentBinding) : RecyclerView.ViewHolder(binding.root){
        init {

        }

        fun bind(listOfMovies: ListOfMovies){

        }

    }

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<ListOfMovies>() {
        override fun areItemsTheSame(oldItem: ListOfMovies, newItem: ListOfMovies): Boolean {
            TODO("Not yet implemented")
        }

        override fun areContentsTheSame(oldItem: ListOfMovies, newItem: ListOfMovies): Boolean {
            TODO("Not yet implemented")
        }

    }




}