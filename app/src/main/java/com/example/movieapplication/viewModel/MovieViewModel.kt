package com.example.movieapplication.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.movieapp.data.entities.Movie
import com.example.movieapplication.data.repo.MovieRepo
import com.example.movieapplication.s
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
class MovieViewModel(private val repo: MovieRepo): ViewModel() {
    private var currentQuerryValue:String?=null
    private var currentSearchResult:Flow<PagingData<Movie>>?=null
    fun searchMovie(queryString: String): Flow<PagingData<Movie>> {
        val lastResult = currentSearchResult
        if (queryString == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = queryString
        val newResult: Flow<PagingData<Movie>> = repo.getSearchMovieResult(queryString).map { pagingData ->
            pagingData.map { it->prepandBaseImageUrl(it)
            }
        }
            .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }

    private fun prepandBaseImageUrl(it: Movie) :Movie{
        var i=it
        i.utlImage="$s${it.poster_path}"
        return i

    }

}


private var currentQueryValue: String? = null

private var currentSearchResult: Flow<PagingData<Movie>>? = null


