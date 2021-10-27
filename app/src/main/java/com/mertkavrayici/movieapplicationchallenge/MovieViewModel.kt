package com.mertkavrayici.movieapplicationchallenge

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.mertkavrayici.movieapplicationchallenge.data.moviedetails.MovieDetails
import com.mertkavrayici.movieapplicationchallenge.remote.MovieInterface
import com.mertkavrayici.movieapplicationchallenge.ui.details.MovieDetailsRepository
import com.mertkavrayici.movieapplicationchallenge.ui.movie.MoviePaging
import com.mertkavrayici.movieapplicationchallenge.utils.Events
import com.mertkavrayici.movieapplicationchallenge.utils.Result
import com.mertkavrayici.movieapplicationchallenge.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val movieInterface: MovieInterface,private val repository:MovieDetailsRepository) :ViewModel() {


    private val query=MutableLiveData<String>()
    val list=query.switchMap {query->
        Pager(PagingConfig(pageSize = 10)) {

            MoviePaging(query, movieInterface)
        }.liveData.cachedIn(viewModelScope)

    }
    fun setQuery(s:String){
        query.postValue(s)
    }

    private val _movieDetails=MutableLiveData<Events<com.mertkavrayici.movieapplicationchallenge.utils.Result<MovieDetails>>>()

    val movieDetails:LiveData<Events<com.mertkavrayici.movieapplicationchallenge.utils.Result<MovieDetails>>> = _movieDetails

    fun getMovieDetails(imdbId:String) =viewModelScope.launch {

        _movieDetails.postValue(Events(Result(Status.LOADING,null,null)))
        _movieDetails.postValue(Events(repository.getMovieDetails(imdbId)))
    }
}