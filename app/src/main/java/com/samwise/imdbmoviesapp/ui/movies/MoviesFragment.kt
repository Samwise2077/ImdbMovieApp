package com.samwise.imdbmoviesapp.ui.movies

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.samwise.imdbmoviesapp.R
import com.samwise.imdbmoviesapp.api.Query
import com.samwise.imdbmoviesapp.data.Movie
import com.samwise.imdbmoviesapp.databinding.GalleryFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first

private const val TAG = "GalleryFragment"

@AndroidEntryPoint
class MoviesFragment : Fragment(R.layout.gallery_fragment) {

    private val viewModel: MoviesViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = GalleryFragmentBinding.bind(view)
        val adapter = MoviesParentAdapter()
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
                            SectionItem("COMING_SOON"), MoviesItem(event.list[0]),
                            SectionItem("MostPopularMovies"), MoviesItem(event.list[1]),
                            SectionItem("IN_THEATERS"), MoviesItem(event.list[2]),
                            SectionItem("Top250Movies"), MoviesItem(event.list[3]),
                            SectionItem("Top250TVs"), MoviesItem(event.list[4])))
                      //  adapter.submitList(listOf(SectionItem("IN_THEATERS")))
                        Log.d(TAG, "onViewCreated: ${event.list[4].typeOfList.name}")
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
}

