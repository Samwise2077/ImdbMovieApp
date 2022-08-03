package com.samwise.imdbmoviesapp.ui.movies

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.samwise.imdbmoviesapp.api.Query
import com.samwise.imdbmoviesapp.data.Movie
import com.samwise.imdbmoviesapp.databinding.ItemParentBinding
import javax.inject.Singleton

private const val TAG = "MoviesParentAdapter"

@Singleton
class MoviesParentAdapter(private val listener: EventListener) : ListAdapter<RecyclerViewItem, MoviesParentAdapter.MoviesParentViewHolder>(DiffUtil()), MoviesChildAdapter.OnItemClickListener{
    val pool = RecyclerView.RecycledViewPool()

    interface EventListener{
        fun onNavigate(movie: Movie)
    }

    var previousQuery: Query? = null
    var listOfAdapters = listOf(listOf(SectionAdapter(),MoviesChildAdapter(this, Query.COMING_SOON)), listOf(SectionAdapter(), MoviesChildAdapter(this, Query.MOST_POPULAR_MOVIES)),
        listOf(SectionAdapter(), MoviesChildAdapter(this, Query.TOP_250_MOVIES)), listOf(SectionAdapter(), MoviesChildAdapter(this, Query.TOP_250_TVs)),
        listOf(SectionAdapter(), MoviesChildAdapter(this, Query.IN_THEATERS)))


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesParentViewHolder {
        Log.d(TAG, "onCreateViewHolder: successfully")
        val binding = ItemParentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesParentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesParentViewHolder, position: Int) {

        val currentItem = getItem(position)

        if (currentItem is MoviesItem) {
            holder.bind(currentItem.listOfMovies)
        }
        else if(currentItem is SectionItem){
            Log.d(TAG, "needed title = ${currentItem.title}")
            holder.bind(currentItem.title)
        }
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

        fun bind(listOfMovies: ListOfMovies){
            Log.d(TAG, "bind: successfully")
            //moviesChildAdapter = MoviesChildAdapter(this@MoviesParentAdapter)

            for(i in listOfAdapters){
                if ((i[1] as MoviesChildAdapter).typeOfQuery == listOfMovies.typeOfList) {
                    if(listOfMovies.typeOfList == previousQuery){
                        Log.d(TAG, "bind: same list - $listOfMovies.typeOfList")
                        (i[1] as MoviesChildAdapter).submitList(listOfMovies.listOfMovies)
                    }
                    else {
                        previousQuery = listOfMovies.typeOfList
                        binding.apply {
                            childRecyclerView.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
                            childRecyclerView.adapter = (i[1] as MoviesChildAdapter)
                        }
                  //      (i[0] as SectionAdapter).submitList(listOf((i[1] as MoviesChildAdapter).typeOfQuery.name))
                        (i[1] as MoviesChildAdapter).submitList(listOfMovies.listOfMovies)
                    }
                    break
                }
            }
        }

        fun bind(title: String){
            Log.d(TAG, "working $title")
            for (i in listOfAdapters){
                if(title == (i[1] as MoviesChildAdapter).typeOfQuery.name){
                    binding.apply {
                        childRecyclerView.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
                        childRecyclerView.adapter = (i[0] as SectionAdapter)
                        childRecyclerView.layout(2, 2, 2, 2)
                    }
                    Log.d(TAG, "working title 2")
                    (i[0] as SectionAdapter).submitList(listOf(title))
                }
            }

        }
    }

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<RecyclerViewItem>() {

        override fun areItemsTheSame(oldItem: RecyclerViewItem, newItem: RecyclerViewItem): Boolean {
            return if(oldItem is MoviesItem && newItem is MoviesItem){
                oldItem.listOfMovies.typeOfList == newItem.listOfMovies.typeOfList
            } else if(oldItem is SectionItem && newItem is SectionItem){
                oldItem.title == newItem.title
            } else false
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: RecyclerViewItem, newItem: RecyclerViewItem): Boolean {
            return oldItem == newItem
        }


    }

    override fun onItemClick(movie: Movie) {
        listener.onNavigate(movie)
    }


}