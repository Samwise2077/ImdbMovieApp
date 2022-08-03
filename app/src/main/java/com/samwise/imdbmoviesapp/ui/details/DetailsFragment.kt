package com.samwise.imdbmoviesapp.ui.details

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.samwise.imdbmoviesapp.R
import com.samwise.imdbmoviesapp.api.Query
import com.samwise.imdbmoviesapp.data.Movie
import com.samwise.imdbmoviesapp.databinding.MovieDetailsFragmentBinding
import com.samwise.imdbmoviesapp.ui.movies.MoviesChildAdapter
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "DetailsFragment"

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.movie_details_fragment), MoviesChildAdapter.OnItemClickListener{

    private val viewModel: DetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = MovieDetailsFragmentBinding.bind(view)

        val adapter = MoviesChildAdapter(this, Query.SIMILARS)
        val reviewsAdapter = ReviewsAdapter()
        val actorsAdapter = ActorsAdapter()
        Log.d(TAG, "onViewCreated: ${viewModel.movie?.year}")
        binding.apply {

            sequelsRecyclerView.adapter = adapter
            sequelsRecyclerView.setHasFixedSize(true)
            sequelsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            titlle1.text = viewModel.movie?.fullTitle
            date.text = viewModel.movie?.year
            country.text = viewModel.movie?.countries
            runtime.text = viewModel.movie?.runtimeStr
            genre.text = viewModel.movie?.genres
            director.text = viewModel.movie?.directors
            /*val actors = mutableListOf("")
            for(i in viewModel.movie?.actorList!!){
                actors.add(i.name)
            }
            cast.text = actors.toString()*/

            Glide.with(imageView)
                .load(viewModel.movie?.image)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView)

            val list = listOf(star1, star2, star3, star4, star5, star6, star7, star8, star9, star10)
            var picture = R.drawable.star2
            val rating = viewModel.movie?.imDbRating?.toDouble()!!
            for(i in 0.. rating.toInt()){
                if(i == rating.toInt() && rating - rating.toInt() >= 0.5){
                   picture = R.drawable.half_star2
                }
                    Glide.with(list[i])
                        .load(picture)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(list[i])
            }

            reviewsRecyclerView.adapter = reviewsAdapter
            reviewsRecyclerView.setHasFixedSize(true)
            reviewsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)


            actorsRecyclerView.adapter = actorsAdapter
            actorsRecyclerView.setHasFixedSize(true)
            actorsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)



        }

        lifecycleScope.launchWhenCreated {
            viewModel.detailsEvent.collect{
                event ->
                run {
                    when (event) {
                        is DetailsViewModel.DetailsEvent.CollectData -> {
                         //   Log.d(TAG, "viewmodel: ${event.list[0].year}")
                            adapter.submitList(event.list)
                            reviewsAdapter.submitList(event.reviews.subList(0, 10))
                            actorsAdapter.submitList(event.actors)
                        }

                        is DetailsViewModel.DetailsEvent.NavigateToDetailsScreen -> {
                            val action = viewModel.selectedMovie?.let {
                                DetailsFragmentDirections.actionDetailsFragmentSelf(it)
                            }

                            if (action != null) {
                                findNavController().navigate(action)
                            }
                        }
                    }
                }
            }
        }
    }



    override fun onItemClick(movie: Movie) {
        viewModel.onMovieDetailsClicked(movie)

    }
}