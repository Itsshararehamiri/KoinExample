package com.example.movieapplication.service

import com.example.movieapplication.MovieImageView

interface ImageLoadingService {
    fun loadImage(imageView:MovieImageView,url:String?)
}