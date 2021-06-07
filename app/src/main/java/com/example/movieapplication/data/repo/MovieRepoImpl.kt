package com.example.movieapplication.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movieapp.data.entities.Movie
import com.example.movieapplication.data.entity.Genre
import com.example.movieapplication.data.repo.source.GenreDataSource
import com.example.movieapplication.service.MovieApiService
import com.example.movieapplication.data.repo.source.MoviePagingSource
import kotlinx.coroutines.flow.Flow

class MovieRepoImpl(private val apiApiService: MovieApiService,val genreDataSource: GenreDataSource) : MovieRepo {
    override fun getSearchMovieResult(query: String):Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { MoviePagingSource(apiApiService, query) }).flow
    }

    override suspend fun getDetailsMovie(id: Int) =apiApiService.getDetailsMovie(id)
    override suspend fun getGenres(): List<Genre> =genreDataSource.getGenre()

    companion object {
        const val PAGE_SIZE = 10
    }

}