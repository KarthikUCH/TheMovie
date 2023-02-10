package com.raju.kvr.themovie.ui.moviedetail

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.raju.kvr.themovie.R
import com.raju.kvr.themovie.databinding.ActivityMovieDetailBinding
import com.raju.kvr.themovie.domain.model.MovieDetail
import com.raju.kvr.themovie.domain.model.toUiData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding

    private val viewModel: MovieDetailViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieId = intent.extras?.getLong(ARG_MOVIE_ID)
            ?: throw IllegalStateException("Pass valid Movie Id")

        setUpActionBar()
        observeViewModel(movieId)
        viewModel.loadMovie(movieId)
    }

    private fun initFavouriteToggleListener() {
        binding.toggleFavourite.setOnCheckedChangeListener { _, isChecked ->
            viewModel.markAsFavourite(isChecked)
        }
    }

    private fun removeFavouriteToggleListener() {
        binding.toggleFavourite.setOnCheckedChangeListener(null)
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

    private fun observeViewModel(movieId: Long) {
        viewModel.movieDetail.observe(this, this::displayMovieDetail)

        viewModel.isFavouriteMovie(movieId).observe(this) { isFavourite ->
            removeFavouriteToggleListener()
            binding.toggleFavourite.isChecked = isFavourite
            binding.toggleFavourite.background =
                if (isFavourite) getDrawable(R.drawable.ic_baseline_favorite) else getDrawable(R.drawable.ic_baseline_favorite_border)
            initFavouriteToggleListener()
        }
    }

    private fun displayMovieDetail(movieDetail: MovieDetail) {

        binding.layoutDetail.visibility = View.VISIBLE

        binding.apply {
            val bgUrl = "https://image.tmdb.org/t/p/original${movieDetail.backdrop}"
            imgBackground.load(bgUrl) {
                placeholder(R.drawable.loading_animation)
                error(R.drawable.ic_baseline_error_outline_24)
            }

            val posterUrl = "https://image.tmdb.org/t/p/original${movieDetail.poster}"
            imgPoster.load(posterUrl) {
                placeholder(R.drawable.loading_animation)
                error(R.drawable.ic_baseline_error_outline_24)
            }

            toolbarLayout.title = movieDetail.title

            tvName.text = movieDetail.title
            tvReleaseDate.text = String.format(
                resources.getString(R.string.label_release_date),
                movieDetail.releaseDate
            )
            tvVoteAverage.text = String.format(
                resources.getString(R.string.label_vote_average),
                movieDetail.voteAverage
            )
            tvVoteCount.text = String.format(
                resources.getString(R.string.label_vote_count),
                movieDetail.voteCount
            )

            if (movieDetail.tagline.isNotBlank()) {
                tvTagline.text = String.format(
                    resources.getString(R.string.label_tagline),
                    movieDetail.tagline
                )
            } else {
                tvTagline.visibility = View.GONE
            }

            if (movieDetail.overview.isNotBlank()) {
                tvOverview.text = String.format(
                    resources.getString(R.string.label_overView),
                    movieDetail.overview
                )
            } else {
                tvOverview.visibility = View.GONE
            }

            if (movieDetail.genres.isNotEmpty()) {
                tvGenre.text = String.format(
                    resources.getString(R.string.label_genre),
                    movieDetail.genres.toUiData()
                )
            } else {
                tvGenre.visibility = View.GONE
            }

            if (movieDetail.languages.isNotEmpty()) {
                tvLanguage.text = String.format(
                    resources.getString(R.string.label_language),
                    movieDetail.languages.toUiData()
                )
            } else {
                tvLanguage.visibility = View.GONE
            }
        }
    }

    companion object {
        const val ARG_MOVIE_ID = "movie_id"
    }
}