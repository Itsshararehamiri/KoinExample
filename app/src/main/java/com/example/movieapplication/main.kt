package com.example.movieapplication

import com.example.movieapplication.service.MovieApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
private const val BASE_URL = "https://api.themoviedb.org/3/"
public val s = "https://image.tmdb.org/t/p/original"
fun createMovieService(): MovieApiService {
    val logger = HttpLoggingInterceptor()
    logger.level = HttpLoggingInterceptor.Level.BASIC

    val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            return@addInterceptor addApiKeyToRequests(chain)
        }

        .build()
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MovieApiService::class.java)
}

private fun addApiKeyToRequests(chain: Interceptor.Chain): okhttp3.Response {

    val request = chain.request().newBuilder()
    val originalHttpUrl = chain.request().url
    val newUrl = originalHttpUrl.newBuilder()
        .addQueryParameter("api_key", "3721a37a13dfd8c83dc2e8ec18aa3944").build()
    request.url(newUrl)
    return chain.proceed(request.build())
}


