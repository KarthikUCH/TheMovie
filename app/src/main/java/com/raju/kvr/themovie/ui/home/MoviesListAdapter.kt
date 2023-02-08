package com.raju.kvr.themovie.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.raju.kvr.themovie.R
import com.raju.kvr.themovie.databinding.MovieListItemBinding
import com.raju.kvr.themovie.domain.model.Movie

class MoviesListAdapter(private val onClick: (Movie) -> Unit) :
    ListAdapter<Movie, MoviesListAdapter.MovieViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            MovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)

        holder.itemView.setOnClickListener {
            onClick(movie)
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }

    class MovieViewHolder(private val binding: MovieListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.apply {
                val imageUrl = "https://image.tmdb.org/t/p/original${movie.poster}"
                imgPoster.load(imageUrl){
                    placeholder(R.drawable.loading_animation)
                    error(R.drawable.ic_baseline_error_outline_24)
                }

                tvName.text = movie.title
                tvReleaseDate.text = String.format(
                    tvReleaseDate.context.getString(R.string.label_release_date),
                    movie.releaseData
                )
                tvVoteAverage.text = String.format(tvVoteAverage.context.getString(R.string.label_vote_average), movie.voteAverage)
                tvVoteCount.text = String.format(tvVoteCount.context.getString(R.string.label_vote_count), movie.voteCount)
            }
        }
    }
}