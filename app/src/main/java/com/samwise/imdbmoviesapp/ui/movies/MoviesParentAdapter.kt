package com.samwise.imdbmoviesapp.ui.movies

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.samwise.imdbmoviesapp.api.Query
import com.samwise.imdbmoviesapp.data.Movie
import com.samwise.imdbmoviesapp.databinding.ItemParentBinding
import javax.inject.Singleton

private const val TAG = "MoviesParentAdapter"

@Singleton
class MoviesParentAdapter : ListAdapter<PagingData<Movie>, MoviesParentAdapter.MoviesParentViewHolder>(DiffUtil()), MoviesChildAdapter.OnItemClickListener,
LifecycleOwner{
    val pool = RecyclerView.RecycledViewPool()
    var currentQuery: Query? = null
    var previousQuery: Query? = null
    var listOfMovies = listOf(MoviesChildAdapter(this, Query.COMING_SOON))

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

    fun updateCurrentQuery(query: Query){
        currentQuery = query
    }


    inner class MoviesParentViewHolder(private val binding: ItemParentBinding) : RecyclerView.ViewHolder(binding.root){
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

        suspend fun bind(pagingData: PagingData<Movie>){
            Log.d(TAG, "bind: successfully")
            //moviesChildAdapter = MoviesChildAdapter(this@MoviesParentAdapter)

            for(i in listOfMovies){
                if (i.typeOfQuery == currentQuery) {
                    if(currentQuery == previousQuery){
                        Log.d(TAG, "bind: same list - $i")

                        i.submitData(pagingData)
                    }
                    else{
                        Log.d(TAG, "bind: new list - $currentQuery")
                        previousQuery = currentQuery
                        binding.apply {
                            childRecyclerView.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
                            childRecyclerView.adapter = i
                        }
                        i.submitData(pagingData)
                    }
                    break
                }
            }
        }
    }


    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<PagingData<Movie>>() {

        override fun areItemsTheSame(
            oldItem: PagingData<Movie>,
            newItem: PagingData<Movie>
        ): Boolean {
            TODO("Not yet implemented")
        }

        override fun areContentsTheSame(
            oldItem: PagingData<Movie>,
            newItem: PagingData<Movie>
        ): Boolean {
            TODO("Not yet implemented")
        }


    }


    override fun onItemClick(movie: Movie) {
        TODO("Not yet implemented")
    }

    override fun getLifecycle(): Lifecycle {
        TODO("Not yet implemented")
    }
}