package com.raju.kvr.themovie.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.raju.kvr.themovie.databinding.FragmentHomeBinding
import com.raju.kvr.themovie.domain.model.Movie
import com.raju.kvr.themovie.ui.moviedetail.MovieDetailActivity
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_CATEGORY = "category"

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var category: String

    private lateinit var binding: FragmentHomeBinding

    private lateinit var adapter: MoviesListAdapter

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            category =
                it.getString(ARG_CATEGORY) ?: throw IllegalStateException("Pass valid arguments")
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
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
    }

    private fun openMovie(movie: Movie) {
        activity?.let {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.apply {
                putExtra(MovieDetailActivity.ARG_MOVIE_ID, movie.id)
                putExtra(MovieDetailActivity.ARG_IS_FAVOURITE, false)
            }
            it.startActivity(intent)
        }
    }

    private fun loadMovies() {
        viewModel.loadMovies(category, 1)
    }

    private fun observeViewModel() {
        viewModel.movies.observe(this.viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(category: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_CATEGORY, category)
                }
            }
    }
}