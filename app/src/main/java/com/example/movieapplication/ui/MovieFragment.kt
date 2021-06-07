package com.example.movieapplication.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.ui.moviedetails.MovieDetailFragment
import com.example.movieapplication.R
import com.example.movieapplication.databinding.MoviesFragmentBinding
import com.example.movieapplication.service.ImageLoadingService
import com.example.movieapplication.viewModel.MovieViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class MovieFragment : Fragment(), MovieAdapter.OnItemClickListener {
    private var searchJob: Job? = null
    val viewModel: MovieViewModel by viewModel()

    lateinit var binding: MoviesFragmentBinding
    val imageLoadingService: ImageLoadingService by inject()
    private val adapter = MovieAdapter(imageLoadingService,this)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MoviesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        var linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.moviesRv.adapter = adapter
            .withLoadStateHeaderAndFooter(header = MovieLoadStateAdapter { adapter.retry() },
                footer = MovieLoadStateAdapter { adapter.retry() })
        adapter.addLoadStateListener { loadState ->
            {
                val isListEmpty =
                    loadState.refresh is LoadState.NotLoading && adapter.itemCount == 0
                // showEmptyList(isListEmpty)

                binding.moviesRv.isVisible = loadState.source.refresh is LoadState.NotLoading
                binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error
            }
        }
        binding.retryButton.setOnClickListener { adapter.retry() }

        binding.moviesRv.layoutManager = linearLayoutManager
        binding.searchEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                search(s.toString())
            }

        })

    }

    private fun search(query: String) {
        // Make sure we cancel the previous job before creating a new one
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchMovie(query).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun onClicked(id: Int) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.f, MovieDetailFragment.newInstance(id)).commit()
    }
}