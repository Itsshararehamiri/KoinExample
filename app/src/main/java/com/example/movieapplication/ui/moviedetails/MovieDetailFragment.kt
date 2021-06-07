package com.example.movieapp.ui.moviedetails


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import org.koin.core.parameter.parametersOf


import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.data.entities.Movie
import com.example.movieapp.ui.moviedetails.MovieDetailFragment
import com.example.movieapplication.MovieImageView
import com.example.movieapplication.R
import com.example.movieapplication.data.entity.DetailsMovie

import com.example.movieapplication.databinding.MoviesFragmentBinding
import com.example.movieapplication.s
import com.example.movieapplication.service.ImageLoadingService
import com.example.movieapplication.viewModel.MovieViewModel
import com.google.gson.Gson
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class MovieDetailFragment : Fragment() {
    val imageLoadingService: ImageLoadingService by inject()
    private lateinit var progressBar: ProgressBar
    private lateinit var container: ConstraintLayout
    private lateinit var titleTv: TextView
    private lateinit var descriptionTv: TextView
    private lateinit var trialerTv: TextView
    private lateinit var voteCountTv: TextView
    private lateinit var originalLanguageTv: TextView
    private lateinit var popularityTv: TextView
    private lateinit var releaseDateTv: TextView
    private lateinit var originalTitleTv: TextView
    private lateinit var voteAverage: TextView
    private lateinit var genresTv: TextView

    private lateinit var image: MovieImageView

    val viewModel: MovieDetailViewModel by viewModel {
        parametersOf(requireArguments().getInt("id"))
    }

    companion object {
        fun newInstance(id: Int) = MovieDetailFragment().apply {
            arguments = bundleOf("id" to id)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movie_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = view.findViewById(R.id.progress_bar)
        container = view.findViewById(R.id.movie_cl)
        originalTitleTv = view.findViewById(R.id.original_title_tv)
        titleTv = view.findViewById(R.id.title)
        originalLanguageTv = view.findViewById(R.id.original_language_tv)
        popularityTv = view.findViewById(R.id.popularity_tv)
        descriptionTv = view.findViewById(R.id.description_tv)
        trialerTv = view.findViewById(R.id.trialer_tv)
        voteCountTv = view.findViewById(R.id.vote_count_tv)
        voteAverage = view.findViewById(R.id.vote_average_tv)
        releaseDateTv = view.findViewById(R.id.release_date_tv)
        genresTv = view.findViewById(R.id.genres_tv)
        image = view.findViewById(R.id.image)

        // arguments?.getInt("id")?.let { viewModel.start(it) }
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.detailsInfo.observe(viewLifecycleOwner, Observer {
            bindCharacter(it.body()!!)
            progressBar.visibility = View.GONE
            container.visibility = View.VISIBLE
        })
    }

    private fun bindCharacter(movie: DetailsMovie) {

        originalTitleTv.text = movie.original_title
        originalLanguageTv.text = movie.original_language
        descriptionTv.text = movie.overview
        popularityTv.text = movie.popularity.toString()
        releaseDateTv.text = movie.release_date
        titleTv.text = movie.title
        voteAverage.text = movie.vote_average.toString()
        voteCountTv.text = movie.vote_count.toString()
        imageLoadingService.loadImage(image, "$s${movie.poster_path}")
        var s = StringBuilder()
        movie.genres.forEach {
            s.append(it.name)
            s.append(',')
        }
        genresTv.text = s.toString().subSequence(0,s.lastIndex-1)


    }
}
