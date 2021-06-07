package com.example.movieapplication.data.repo.source


import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieapp.data.entities.Movie
import com.example.movieapplication.common.STARTING_PAGE_INDEX
import com.example.movieapplication.data.entity.Genre
import com.example.movieapplication.data.repo.MovieRepoImpl.Companion.PAGE_SIZE
import com.example.movieapplication.service.MovieApiService
import retrofit2.HttpException
import java.io.IOException
//Pager: This API consumes whatever the RemoteMediator or PagingSource returns as a data source to it and returns a reactive stream of PagingData.


class MoviePagingSource(private val service: MovieApiService, private val query: String) :
    PagingSource<Int, Movie>(),GenreDataSource {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: STARTING_PAGE_INDEX
        val query = query
        return try {
        val response=service.serachMovies(query,position)

            println("kkkkkkkkkkk")
            println(response)
           val movies= response.body()!!.results
            val nextKey=if(movies.isEmpty()){
               null
            }
            else{
                position + (params.loadSize / PAGE_SIZE)
            }
            LoadResult.Page(data = movies,nextKey=nextKey,prevKey = if(position== STARTING_PAGE_INDEX)null else position-1)
        }
        catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

        override suspend fun getGenre(): List<Genre> =service.getGenre()



}