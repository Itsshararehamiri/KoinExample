package com.example.movieapplication.data.entity

import com.example.movieapp.data.entities.Movie

data class MovieList(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)