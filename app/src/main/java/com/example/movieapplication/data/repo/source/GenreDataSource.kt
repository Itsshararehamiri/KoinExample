package com.example.movieapplication.data.repo.source

import com.example.movieapplication.data.entity.Genre

interface GenreDataSource {
    suspend   fun getGenre():List<Genre>

}