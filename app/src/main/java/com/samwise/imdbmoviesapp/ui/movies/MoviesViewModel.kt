package com.samwise.imdbmoviesapp.ui.movies

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samwise.imdbmoviesapp.api.ImdbResponse
import com.samwise.imdbmoviesapp.api.Query
import com.samwise.imdbmoviesapp.data.ListOfMovies
import com.samwise.imdbmoviesapp.data.Movie
import com.samwise.imdbmoviesapp.data.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "MoviesViewModel"

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {
   var movies: MutableList<ListOfMovies> = mutableListOf()
   var selectedMovie: Movie? = null
    private val moviesEventChannel = Channel<MoviesEvent>()
   val moviesEvent = moviesEventChannel.receiveAsFlow()

   init {
       viewModelScope.launch {
           movies.add(ListOfMovies(Query.COMING_SOON, ImdbResponse(repository.getMoviesComingSoon(),"")))
           movies.add(ListOfMovies(Query.MOST_POPULAR_MOVIES,
               ImdbResponse(
               repository.getMostPopularMovies().subList(0, if(repository.getMostPopularMovies().size > 20 ) 20
               else repository.getMostPopularMovies().size), "")))
           movies.add(ListOfMovies(Query.IN_THEATERS,
               ImdbResponse(repository.getMoviesInTheaters(), "")))
           movies.add(ListOfMovies(Query.TOP_250_MOVIES,
               ImdbResponse(repository.getTop250Movies().subList(0, 20), "")))
           movies.add(ListOfMovies(Query.TOP_250_TVs,
               ImdbResponse(repository.getTop250Tvs().subList(0, 20), "")))
           moviesEventChannel.send(MoviesEvent.CollectMovies(movies))
       }
   }

   fun onMovieDetailsClicked(movie: Movie){
       viewModelScope.launch {
           Log.d(TAG, "onMovieDetailsClicked: ${movie.year}")
           selectedMovie = repository.getMovie(id = movie.id)
           selectedMovie!!.imDbRating = movie.imDbRating
           moviesEventChannel.send(MoviesEvent.NavigateToDetailsScreen(selectedMovie!!))


       }
   }

    sealed class MoviesEvent{
         data class CollectMovies(val list: List<ListOfMovies>) : MoviesEvent()
         data class NavigateToDetailsScreen(val movie: Movie) : MoviesEvent()
    }
}