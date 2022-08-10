package com.samwise.imdbmoviesapp.ui.details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samwise.imdbmoviesapp.data.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


private const val TAG = "DetailsViewModel"

@HiltViewModel
class DetailsViewModel @Inject constructor(private val state: SavedStateHandle, private val repository: MoviesRepository) : ViewModel(){
    private val detailsEventChannel = Channel<DetailsEvent>()
    val detailsEvent = detailsEventChannel.receiveAsFlow()
    private val similars: MutableList<Movie> = mutableListOf()
    val movie = state.get<Movie>("movie")
    var selectedMovie: Movie? = null
    val length = 0

    init {
        viewModelScope.launch {
            Log.d("details view model", "${movie?.similars}")
            if(movie?.similars != null){
                var seriesTitle = ""
                for((idx, value) in movie.title.withIndex()){
                    if(value == ':' || ((value.isDigit()) && !movie.title[idx + 1].isDigit())){
                        break
                    }
                    seriesTitle += value

                }
                var title = ""
                Log.d(TAG, "seriesTitle = ${seriesTitle}")
                for(element in movie.similars){
                    Log.d(TAG, "title = $title ")
                    title = ""
                    Log.d(TAG, seriesTitle)
                    for((idx, i) in element.title.withIndex()){
                        title += i
                        if(title == seriesTitle){
                            Log.d(TAG, "working")
                            similars.add(repository.getMovie(element.id))

                            break
                        }
                    }
                }
                val reviews = repository.getReviews(movie.id)
                detailsEventChannel.send(DetailsEvent.CollectData(similars, reviews.items, movie.actorList))
            }
        }
    }

    fun onMovieDetailsClicked(movie: Movie) {
        viewModelScope.launch {
            selectedMovie = repository.getMovie(id = movie.id)
            selectedMovie!!.imDbRating = movie.imDbRating
            detailsEventChannel.send(DetailsEvent.NavigateToDetailsScreen(selectedMovie!!))

        }
    }

    fun onReviewClicked(review: ReviewDetail) {
        viewModelScope.launch {
            detailsEventChannel.send(DetailsEvent.NavigateToReviewsDetail(review))
        }
    }

    sealed class DetailsEvent{
        data class CollectData(val list: List<Movie>, val reviews: List<ReviewDetail>, val actors: List<ActorShort>): DetailsEvent()
        data class NavigateToDetailsScreen(val movie: Movie) : DetailsEvent()
        data class NavigateToReviewsDetail(val review: ReviewDetail) : DetailsEvent()
    }

}