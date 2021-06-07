package com.example.movieapplication.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.data.entities.Movie
import com.example.movieapplication.MovieImageView
import com.example.movieapplication.R
import com.example.movieapplication.service.ImageLoadingService
import org.koin.android.ext.android.inject

class MovieAdapter(val imageLoadingService: ImageLoadingService,val onItemClickListener: OnItemClickListener):PagingDataAdapter<Movie, MovieAdapter.MovieViewHolder>(REPO_COMPARATOR) {

 inner   class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.title_tv)
        var description: TextView = view.findViewById(R.id.description_tv)
        var trialer: TextView = view.findViewById(R.id.trialer_tv)
        var genres: TextView = view.findViewById(R.id.genres_tv);
        var voteCount: TextView = view.findViewById(R.id.vote_count_tv);
        var imageView: MovieImageView = view.findViewById(R.id.image_view);
        fun bindMovie(movie: Movie) {
            title.text = movie.original_title
            description.text = movie.overview
            trialer.text = movie.original_language
            /*if (movie.genre_ids.size > 0)
                genres.text = movie.genre_ids[0].toString()*/
            voteCount.text = movie.vote_count.toString()
            //  imageLoaderService.loadImage(imageView, "")// TODO: 03/04/2021
            //  listener?.onItemClicked("l")
          /*  Glide.with(itemBinding.root)
                .load("$s${item.poster_path}")
                .into(itemBinding.imageView)*/
            imageLoadingService.loadImage(imageView = imageView,url = movie.utlImage)
            itemView.setOnClickListener { onItemClickListener.onClicked(movie.id) }

        }

    }
    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let {
            println("kkkkkkkkkkkkkk")
            println(it.id)
            holder.bindMovie(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        MovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layput_item_movie,parent,false))
    interface OnItemClickListener{
        fun onClicked(id:Int)
    }
}