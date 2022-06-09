package com.samwise.imdbmoviesapp.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samwise.imdbmoviesapp.api.Query
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
   private val moviesEventChannel = Channel<MoviesEvent>()
   val moviesEvent = moviesEventChannel.receiveAsFlow()

   init {
       viewModelScope.launch {
           movies.add(ListOfMovies(repository.getMoviesComingSoon(), Query.COMING_SOON))
           movies.add(ListOfMovies(repository.getMostPopularMovies(), Query.MostPopularMovies))
           movies.add(ListOfMovies(repository.getMoviesInTheaters(), Query.IN_THEATERS))
           movies.add(ListOfMovies(repository.getTop250Movies(), Query.Top250Movies))
           movies.add(ListOfMovies(repository.getTop250Tvs(), Query.Top250TVs))
           moviesEventChannel.send(MoviesEvent.CollectMovies(movies))
       }
   }

    sealed class MoviesEvent{
         data class CollectMovies(val list: List<ListOfMovies>) : MoviesEvent()
         data class NavigateToDetailsScreen(val movie: Movie) : MoviesEvent()
    }
}