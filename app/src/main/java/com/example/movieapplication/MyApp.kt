package com.example.movieapplication

import android.app.Application
import android.os.Bundle
import com.example.movieapp.ui.moviedetails.MovieDetailFragment
import com.example.movieapp.ui.moviedetails.MovieDetailViewModel
import com.example.movieapplication.data.repo.MovieRepoImpl
import com.example.movieapplication.data.repo.MovieRepo
import com.example.movieapplication.data.repo.source.GenreDataSource
import com.example.movieapplication.data.repo.source.GenreRemoteDataSource
import com.example.movieapplication.service.FrescoImageLoadingServiceImpl
import com.example.movieapplication.service.ImageLoadingService
import com.example.movieapplication.service.MovieApiService
import com.example.movieapplication.viewModel.MovieViewModel
import com.facebook.drawee.backends.pipeline.Fresco
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyApp: Application() {


    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this);

        val appModule = module {
            single { createMovieService()  }
            factory<GenreDataSource> {GenreRemoteDataSource(get() )  }
            factory<MovieRepo> { MovieRepoImpl(get(),get()) }
            viewModel { MovieViewModel(get()) }
            viewModel { (id:Int)->MovieDetailViewModel(get(),id) }
            single <ImageLoadingService> { FrescoImageLoadingServiceImpl() }

        }
        startKoin{
            androidLogger()
            androidContext(this@MyApp)
            modules(appModule)
        }
    }
}
