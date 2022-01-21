package com.samwise.imdbmoviesapp.ui.movies

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.samwise.imdbmoviesapp.data.Movie
import com.samwise.imdbmoviesapp.databinding.ItemParentBinding

private const val TAG = "MoviesParentAdapter"

class MoviesParentAdapter : PagingDataAdapter<ListOfMovies, MoviesParentAdapter.MoviesParentViewHolder>(DiffUtil()), MoviesChildAdapter.OnItemClickListener{
         val pool = RecyclerView.RecycledViewPool()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesParentViewHolder {
            Log.d(TAG, "onCreateViewHolder: successfully")
        val binding = ItemParentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesParentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesParentViewHolder, position: Int) {


        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }


    inner class MoviesParentViewHolder(private val binding: ItemParentBinding) : RecyclerView.ViewHolder(binding.root){
        lateinit var moviesChildAdapter: MoviesChildAdapter
        init {
            binding.root.setOnClickListener {
               val position = bindingAdapterPosition
               if(position != RecyclerView.NO_POSITION){
                   val item = getItem(position)
                   if(item != null){

                   }
               }
            }
        }

        fun bind(listOfMovies: ListOfMovies){
            Log.d(TAG, "bind: successfully")
            moviesChildAdapter = MoviesChildAdapter(this@MoviesParentAdapter)
            binding.apply {
                childRecyclerView.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
                childRecyclerView.adapter = moviesChildAdapter
            }
            moviesChildAdapter.submitList(listOfMovies.listOfMovies)
        }
    }

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<ListOfMovies>() {
        override fun areItemsTheSame(oldItem: ListOfMovies, newItem: ListOfMovies): Boolean {
            return oldItem.typeOfList == newItem.typeOfList
        }

        override fun areContentsTheSame(oldItem: ListOfMovies, newItem: ListOfMovies): Boolean {
            return oldItem == newItem
        }

    }


    override fun onItemClick(movie: Movie) {
        TODO("Not yet implemented")
    }
}