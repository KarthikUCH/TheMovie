package com.raju.kvr.themovie.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.raju.kvr.themovie.R
import com.raju.kvr.themovie.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeViewModel()
    }

    private fun observeViewModel() {
        mainViewModel.status.observe(this) {
            when (it) {
                MainViewModel.Status.LOADING -> {
                    displayLoading()
                }
                MainViewModel.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    binding.txtViewStatus.text = "Success"
                    // TODO : Display Home Page

                }
                MainViewModel.Status.ERROR -> {
                    displayError()
                }
            }
        }
    }

    private fun displayLoading() {
        binding.apply {
            imgViewStatus.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            txtViewStatus.text = resources.getString(R.string.status_loading)
        }
    }

    private fun displayError() {
        binding.apply {
            imgViewStatus.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
            txtViewStatus.text = resources.getString(R.string.status_Error)
        }
    }
}