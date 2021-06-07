package com.example.movieapplication.data.repo

import androidx.paging.PagingData
import com.example.movieapp.data.entities.Movie
import com.example.movieapplication.data.entity.DetailsMovie
import com.example.movieapplication.data.entity.Genre
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface MovieRepo {
    fun getSearchMovieResult(query: String): Flow<PagingData<Movie>>
    suspend fun getDetailsMovie(id: Int): Response<DetailsMovie>
    suspend fun getGenres():List<Genre>



}