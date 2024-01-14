package com.raju.kvr.themovie.ui.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.raju.kvr.themovie.R
import com.raju.kvr.themovie.databinding.FragmentFavouriteMovieBinding
import com.raju.kvr.themovie.domain.model.Movie
import com.raju.kvr.themovie.ui.home.MoviesListAdapter
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * Use the [FavouriteMovieFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class FavouriteMovieFragment : Fragment() {

    private lateinit var binding: FragmentFavouriteMovieBinding

    private lateinit var adapter: MoviesListAdapter

    private val viewModel: FavouriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouriteMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideMainActionBar()
        setUpActionBar()
        initRecyclerView()
        observeViewModel()
    }

    private fun hideMainActionBar() {
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    private fun setUpActionBar() {
        binding.toolbar.setNavigationIcon(R.drawable.arrow_back)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }


    private fun initRecyclerView() {
        adapter = MoviesListAdapter(this::openMovie)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
    }

    private fun observeViewModel() {
        viewModel.getMovies().observe(this.viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun openMovie(movie: Movie) {
        val action =
            FavouriteMovieFragmentDirections.actionFavouriteMovieFragmentToMovieDetailFragment(movie.id)
        findNavController().navigate(action)
    }
}