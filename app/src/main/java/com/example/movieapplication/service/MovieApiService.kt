package com.example.movieapplication.service

import com.example.movieapp.data.entities.Movie
import com.example.movieapplication.data.entity.DetailsMovie
import com.example.movieapplication.data.entity.Genre
import com.example.movieapplication.data.entity.MovieList
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Github API communication setup via Retrofit.
 */
interface MovieApiService {
    /**
     * Get repos ordered by stars.
     */
    @GET("search/movie")
  suspend   fun serachMovies(
        @Query("query") query: String,
        @Query("page") page: Int
        //,
        //   @Query("per_page") itemsPerPage: Int
    ): Response<MovieList>
    @GET("discover/movie")
    suspend fun getAllMovies() : Response<MovieList>

//https://api.themoviedb.org/3/movie/{movie_id}?api_key=<<api_key>>&language=en-US
    @GET("movie/{movie_id}")
    suspend fun getDetailsMovie(

        @Path("movie_id") movieId: Int
        //,
        //   @Query("per_page") itemsPerPage: Int
    ): Response<DetailsMovie>
    //https://api.themoviedb.org/3/genre/movie/list?api_key=3721a37a13dfd8c83dc2e8ec18aa3944&language=en-US
    @GET("genre/movie/list")
    suspend fun getGenre():List<Genre>








}
