package com.raju.kvr.themovie.ui.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.raju.kvr.themovie.R
import com.raju.kvr.themovie.databinding.FragmentMovieDetailBinding
import com.raju.kvr.themovie.domain.model.MovieDetail
import com.raju.kvr.themovie.domain.model.toUiData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [MovieDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailBinding

    private val viewModel: MovieDetailViewModel by viewModels()

    val saveArgs: MovieDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieId = saveArgs.movieId

        hideMainActionBar()
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

    private fun hideMainActionBar() {
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    private fun setUpActionBar() {
        binding.toolbar.setNavigationIcon(R.drawable.arrow_back)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }


    private fun observeViewModel(movieId: Long) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.movieDetail.collect() {
                        displayMovieDetail(it)
                    }
                }

                launch {
                    viewModel.isFavouriteMovie(movieId).collect() { isFavourite ->
                        removeFavouriteToggleListener()
                        binding.toggleFavourite.isChecked = isFavourite
                        binding.toggleFavourite.background =
                            if (isFavourite) activity?.getDrawable(R.drawable.ic_baseline_favorite) else activity?.getDrawable(
                                R.drawable.ic_baseline_favorite_border
                            )
                        initFavouriteToggleListener()
                    }
                }
            }
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
                resources.getString(R.string.label_release_date), movieDetail.releaseDate
            )
            tvVoteAverage.text = String.format(
                resources.getString(R.string.label_vote_average), movieDetail.voteAverage
            )
            tvVoteCount.text = String.format(
                resources.getString(R.string.label_vote_count), movieDetail.voteCount
            )

            if (movieDetail.tagline.isNotBlank()) {
                tvTagline.text = String.format(
                    resources.getString(R.string.label_tagline), movieDetail.tagline
                )
            } else {
                tvTagline.visibility = View.GONE
            }

            if (movieDetail.overview.isNotBlank()) {
                tvOverview.text = String.format(
                    resources.getString(R.string.label_overView), movieDetail.overview
                )
            } else {
                tvOverview.visibility = View.GONE
            }

            if (movieDetail.genres.isNotEmpty()) {
                tvGenre.text = String.format(
                    resources.getString(R.string.label_genre), movieDetail.genres.toUiData()
                )
            } else {
                tvGenre.visibility = View.GONE
            }

            if (movieDetail.languages.isNotEmpty()) {
                tvLanguage.text = String.format(
                    resources.getString(R.string.label_language), movieDetail.languages.toUiData()
                )
            } else {
                tvLanguage.visibility = View.GONE
            }
        }
    }

}