package com.example.movieapplication.service

import com.example.movieapplication.MovieImageView
import com.facebook.drawee.view.SimpleDraweeView
import java.lang.IllegalStateException

class FrescoImageLoadingServiceImpl:ImageLoadingService {
    override fun loadImage(imageView: MovieImageView, url: String?) {
        if(imageView is SimpleDraweeView)
            imageView.setImageURI(url)
        else throw IllegalStateException("Imageview is not an instance of SimpleDraweeView")
    }
}