package com.example.movieapplication.data.repo.source

import com.example.movieapplication.data.entity.Genre
import com.example.movieapplication.service.MovieApiService

class GenreRemoteDataSource(val service: MovieApiService):GenreDataSource {
    override suspend fun getGenre(): List<Genre> =service.getGenre()

}