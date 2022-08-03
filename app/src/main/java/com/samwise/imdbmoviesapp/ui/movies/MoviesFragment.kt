package com.samwise.imdbmoviesapp.ui.movies

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.samwise.imdbmoviesapp.R
import com.samwise.imdbmoviesapp.api.Query
import com.samwise.imdbmoviesapp.data.Movie
import com.samwise.imdbmoviesapp.databinding.GalleryFragmentBinding
import com.samwise.imdbmoviesapp.databinding.MovieDetailsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Objects.isNull

private const val TAG = "GalleryFragment"

@AndroidEntryPoint
class MoviesFragment : Fragment(R.layout.gallery_fragment), MoviesParentAdapter.EventListener {

    private val viewModel: MoviesViewModel by viewModels()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = GalleryFragmentBinding.bind(view)
        val adapter = MoviesParentAdapter(this)
        binding.apply {
            parentRecyclerView.setHasFixedSize(true)
            parentRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            parentRecyclerView.adapter = adapter
        }

        lifecycleScope.launchWhenCreated {
            viewModel.moviesEvent.collect{ event ->
                when(event){
                    is MoviesViewModel.MoviesEvent.CollectMovies ->{
                        adapter.submitList(listOf(
                            SectionItem(Query.COMING_SOON.name), MoviesItem(event.list[0]),
                            SectionItem(Query.MOST_POPULAR_MOVIES.name), MoviesItem(event.list[1]),
                            SectionItem(Query.IN_THEATERS.name), MoviesItem(event.list[2]),
                            SectionItem(Query.TOP_250_MOVIES.name), MoviesItem(event.list[3]),
                            SectionItem(Query.TOP_250_TVs.name), MoviesItem(event.list[4])))

                        Log.d(TAG, "onViewCreated: ${event.list[4].typeOfList.name}")
                    }

                    is MoviesViewModel.MoviesEvent.NavigateToDetailsScreen -> {
                        val action = viewModel.selectedMovie?.let {
                            MoviesFragmentDirections.actionMoviesFragmentToDetailsFragment2(it)
                        }

                        Log.d(TAG, "onScreen ${event.movie.imDbRating}")

                        try {
                            if (action != null) {
                                findNavController().navigate(action)
                            }

                        }
                        catch (exception: NullPointerException){
                            Log.e(TAG, "onViewCreated: ", exception)
                        }
                    }
                }
            }
        }
   }


   private inline fun <reified T> Any?.tryCast(block: T.() -> Unit) {
        if (this is T) {
            block()
        }
    }

    override fun onNavigate(movie: Movie) {
        viewModel.onMovieDetailsClicked(movie)
    }
}

