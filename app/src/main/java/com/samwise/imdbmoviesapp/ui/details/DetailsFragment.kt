package com.samwise.imdbmoviesapp.ui.details

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.marginTop
import androidx.core.view.size
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
import com.samwise.imdbmoviesapp.data.ReviewDetail
import com.samwise.imdbmoviesapp.databinding.MovieDetailsFragmentBinding
import com.samwise.imdbmoviesapp.ui.OnItemClickListener
import com.samwise.imdbmoviesapp.ui.movies.adapters.MoviesChildAdapter
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "DetailsFragment"

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.movie_details_fragment),
    ReviewsAdapter.OnItemClickListener, OnItemClickListener {

    private val viewModel: DetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = MovieDetailsFragmentBinding.bind(view)

        val adapter = MoviesChildAdapter(this, Query.SIMILARS)
        val reviewsAdapter = ReviewsAdapter(this)
        val actorsAdapter = ActorsAdapter()

        Log.d(TAG, "onViewCreated: ${viewModel.movie?.year}")
        binding.apply {
           // dragview.

            sequelsRecyclerView.adapter = adapter
            sequelsRecyclerView.setHasFixedSize(true)
            sequelsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            sequelsRecyclerView.size

            fullTitle.text = viewModel.movie?.fullTitle
            date.text = viewModel.movie?.year
            country.text = viewModel.movie?.countries
            runtime.text = viewModel.movie?.runtimeStr
            genre.text = viewModel.movie?.genres
            director.text = viewModel.movie?.directors
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

            slidingLayout.setOnClickListener {

            }

            actorsRecyclerView.adapter = actorsAdapter
            actorsRecyclerView.setHasFixedSize(true)
            actorsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            slidingLayout.isTouchEnabled = false



        }

        lifecycleScope.launchWhenCreated {
            viewModel.detailsEvent.collect{
                event ->
                run {
                    when (event) {
                        is DetailsViewModel.DetailsEvent.CollectData -> {
                            adapter.submitList(event.sequels)
                            if(event.sequels.isEmpty()){
                                binding.apply {

                                    val constraintSet = ConstraintSet()
                                    constraintSet.clone(constraintLayout)
                                    constraintSet.connect(ratingTextView.id, ConstraintSet.TOP, imageView.id, ConstraintSet.BOTTOM)
                                    constraintSet.applyTo(constraintLayout)
                                    val ratingLayoutParams = ratingTextView.layoutParams as ConstraintLayout.LayoutParams
                                    val sequelsLayoutParams = sequelsTextView.layoutParams as ConstraintLayout.LayoutParams
                                    ratingLayoutParams.topMargin = sequelsLayoutParams.topMargin
                                    sequelsTextView.isInvisible= true
                                    sequelsRecyclerView.isInvisible = true

                                 //   ratingTextView.mar
                                }
                            }
                           if(event.reviews.size > 10){
                               reviewsAdapter.submitList(event.reviews.subList(0, 10))
                           }else{
                               reviewsAdapter.submitList(event.reviews)
                           }

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

                        is DetailsViewModel.DetailsEvent.NavigateToReviewsDetail -> {
                            Log.d(TAG, "onViewCreated: enjoi")
                            binding.apply {
                                slidingLayout.panelState = SlidingUpPanelLayout.PanelState.EXPANDED
                                cardView.visibility = View.VISIBLE
                                event.review.apply {
                                    userNameTextView.text = username
                                    dateTextView.text = date
                                    contentTxtView.text = content
                                }
                                quitImageView.setOnClickListener {
                                   slidingLayout.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED

                                }
                            }
                        }
                    }
                }
            }
        }
    }



    override fun onMovieClick(movie: Movie) {
        viewModel.onMovieDetailsClicked(movie)

    }

    override fun onReviewClick(review: ReviewDetail) {
        viewModel.onReviewClicked(review)
    }
}