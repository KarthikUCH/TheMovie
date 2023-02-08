package com.raju.kvr.themovie.ui.moviedetail

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.raju.kvr.themovie.R
import com.raju.kvr.themovie.databinding.ActivityMovieDetailBinding
import com.raju.kvr.themovie.domain.model.MovieDetail
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
        val isFavourite = intent.extras?.getBoolean(ARG_IS_FAVOURITE, false)
            ?: throw IllegalStateException("Pass valid arguments")

        setUpActionBar()
        observeViewModel()
        viewModel.loadMovie(movieId, isFavourite)
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

    private fun observeViewModel() {
        viewModel.movieDetail.observe(this, this::displayMovieDetail)

        viewModel.isFavourite.observe(this) {
            binding.toggleFavourite.isChecked = it
        }
    }

    private fun displayMovieDetail(movieDetail: MovieDetail) {

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
                movieDetail.releaseData
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
        }
    }

    companion object {
        const val ARG_MOVIE_ID = "movie_id"
        const val ARG_IS_FAVOURITE = "is_favourite"
    }
}