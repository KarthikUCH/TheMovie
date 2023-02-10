package com.raju.kvr.themovie.ui.favourite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.raju.kvr.themovie.R
import com.raju.kvr.themovie.databinding.ActivityFavouriteBinding
import com.raju.kvr.themovie.domain.model.Movie
import com.raju.kvr.themovie.ui.home.MoviesListAdapter
import com.raju.kvr.themovie.ui.moviedetail.MovieDetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavouriteBinding

    private lateinit var adapter: MoviesListAdapter

    private val viewModel: FavouriteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpActionBar()
        initRecyclerView()
        observeViewModel()
    }

    private fun setUpActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun initRecyclerView() {
        adapter = MoviesListAdapter(this::openMovie)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun observeViewModel() {
        viewModel.getMovies().observe(this) {
            adapter.submitList(it)
        }
    }

    private fun openMovie(movie: Movie) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.apply {
            putExtra(MovieDetailActivity.ARG_MOVIE_ID, movie.id)
        }
        startActivity(intent)
    }
}