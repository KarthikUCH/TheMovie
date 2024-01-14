package com.raju.kvr.themovie.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.raju.kvr.themovie.databinding.FragmentHomeBinding
import com.raju.kvr.themovie.domain.model.Movie
import com.raju.kvr.themovie.ui.MainFragmentDirections
import com.raju.kvr.themovie.ui.search.SearchMovieFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

private const val ARG_CATEGORY = "category"
private const val ARG_IS_SEARCH = "is_search"

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var category: String
    private var isSearch: Boolean = false

    private lateinit var binding: FragmentHomeBinding

    private lateinit var adapter: MoviesListAdapter

    private val viewModel: HomeViewModel by viewModels()

    private var isLoading: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            category =
                it.getString(ARG_CATEGORY) ?: throw IllegalStateException("Pass valid arguments")
            isSearch = it.getBoolean(ARG_IS_SEARCH, false)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observeViewModel()
        loadMovies()
    }

    private fun initRecyclerView() {
        adapter = MoviesListAdapter(this::openMovie)
        binding.apply {
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(context)
            addRecyclerViewScrollListener()
        }
    }

    private fun addRecyclerViewScrollListener() {
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (!isLoading && linearLayoutManager.findLastCompletelyVisibleItemPosition() == adapter.itemCount - 1) {
                    isLoading = true
                    viewModel.loadMoreMovies()
                }
            }
        })
    }


    private fun openMovie(movie: Movie) {
        if(isSearch){
            val action =
                SearchMovieFragmentDirections.actionSearchMovieFragmentToMovieDetailFragment(movieId = movie.id)
            findNavController().navigate(action)
        } else {
            val action =
                MainFragmentDirections.actionHomeFragmentToMovieDetailFragment(movieId = movie.id)
            findNavController().navigate(action)
        }
    }

    private fun loadMovies() {
        viewModel.loadMovies(category, 1, isSearch)
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    viewModel.movies.collect() {
                        adapter.submitList(it)
                    }
                }

                launch {
                    viewModel.status.collect {
                        when (it) {
                            HomeViewModel.Status.LOADING -> {}
                            HomeViewModel.Status.SUCCESS -> {
                                isLoading = false
                            }

                            HomeViewModel.Status.ERROR -> {
                                isLoading = false
                            }
                        }
                    }
                }
            }
        }

    }

    companion object {

        @JvmStatic
        fun newInstance(category: String, isSearch: Boolean = false) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_CATEGORY, category)
                    putBoolean(ARG_IS_SEARCH, isSearch)
                }
            }
    }
}