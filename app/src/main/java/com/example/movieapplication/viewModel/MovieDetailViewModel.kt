package com.example.movieapp.ui.moviedetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.entities.Movie
import com.example.movieapplication.data.entity.DetailsMovie
import com.example.movieapplication.data.repo.MovieRepo
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Response

class MovieDetailViewModel(
    private val repository: MovieRepo, val id: Int
) : ViewModel() {
    val detailsInfo = MutableLiveData<Response<DetailsMovie>>()

    init {
        viewModelScope.launch {
            detailsInfo.value=repository.getDetailsMovie(id)
        }
    }


}
