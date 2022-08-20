package com.samwise.imdbmoviesapp.ui.movies

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.samwise.imdbmoviesapp.R
import com.samwise.imdbmoviesapp.api.ImdbResponse
import com.samwise.imdbmoviesapp.api.Query
import com.samwise.imdbmoviesapp.data.ListOfMovies
import com.samwise.imdbmoviesapp.data.Movie
import com.samwise.imdbmoviesapp.databinding.GalleryFragmentBinding
import com.samwise.imdbmoviesapp.ui.movies.adapters.MoviesItem
import com.samwise.imdbmoviesapp.ui.movies.adapters.MoviesParentAdapter
import com.samwise.imdbmoviesapp.ui.movies.adapters.RecyclerViewItem
import com.samwise.imdbmoviesapp.ui.movies.adapters.SectionItem
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "GalleryFragment"

@AndroidEntryPoint
class MoviesFragment : Fragment(R.layout.gallery_fragment), MoviesParentAdapter.EventListener {

    private val viewModel: MoviesViewModel by viewModels()
    var listOfMovies = mutableListOf<RecyclerViewItem>()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = GalleryFragmentBinding.bind(view)
        val adapter = MoviesParentAdapter(this)
      //  val list = ListOfMovies(Query.MOVIE, ImdbResponse())
        binding.apply {
            parentRecyclerView.setHasFixedSize(true)
            parentRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            parentRecyclerView.adapter = adapter
          //  parentRecyclerView.visibility = View.INVISIBLE

        }

        viewModel.mostPopularMovies.observe(viewLifecycleOwner){
            result ->
            if(viewModel.shouldCache){
                Log.d(TAG, "caching...")
                for (i in result.data!!){
                    Log.d(TAG, "1$i\n")
                }
                listOfMovies[3] = MoviesItem(result.data[result.data.size - 1])
                adapter.submitList(listOfMovies)
            }
        }
        viewModel.inTheaters.observe(viewLifecycleOwner){
                result ->
            if(viewModel.shouldCache){
                Log.d(TAG, "caching...")
                for (i in result.data!!){
                    Log.d(TAG, "1$i\n")
                }
                listOfMovies[5] = MoviesItem(result.data[result.data.size - 1])
                adapter.submitList(listOfMovies)


            }
        }

        viewModel.top250M.observe(viewLifecycleOwner){
                result ->
            if(viewModel.shouldCache){
                Log.d(TAG, "caching...")
                for (i in result.data!!){
                    Log.d(TAG, "1$i\n")
                }
                listOfMovies[7] = MoviesItem(result.data[result.data.size - 1])
                adapter.submitList(listOfMovies)


            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.moviesEvent.collect{ event ->
                when(event){
                    is MoviesViewModel.MoviesEvent.CollectMovies ->{
                        listOfMovies = mutableListOf(
                            SectionItem(Query.COMING_SOON.name), MoviesItem(event.list[0]),
                            SectionItem(Query.MOST_POPULAR_MOVIES.name), MoviesItem(event.list[1]),
                            SectionItem(Query.IN_THEATERS.name), MoviesItem(event.list[2]),
                            SectionItem(Query.TOP_250_MOVIES.name), MoviesItem(event.list[3]),
                            SectionItem(Query.TOP_250_TVs.name), MoviesItem(event.list[4])
                        )
                        adapter.submitList(listOfMovies)
                        viewModel.comingSoon = event.list[0]
                        viewModel.top250T = event.list[4]
                        binding.apply {
                            parentRecyclerView.visibility = View.VISIBLE
                        }

                        Log.d(TAG, "onViewCreated: ${event.list[4].typeOfList?.name}")
                        viewModel.shouldCache = true
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


    override fun onNavigate(movie: Movie) {
        viewModel.onMovieDetailsClicked(movie)
    }
}

