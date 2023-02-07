package com.raju.kvr.themovie.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.raju.kvr.themovie.R
import com.raju.kvr.themovie.databinding.ActivityMainBinding
import com.raju.kvr.themovie.ui.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var pagerAdapter: ViewPagerAdapter

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
                    displayHomePage()

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

    private fun displayHomePage() {

        initViewPager()
        initTabLayout()

        binding.apply {
            layoutHome.visibility = View.VISIBLE
            layoutMain.visibility = View.GONE
        }
    }

    private fun initTabLayout() {
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = when (position) {
                1 -> "Now Playing"
                2 -> "Popular"
                3 -> "Top Rated"
                else -> "Upcoming"
            }
        }.attach()
    }

    private fun initViewPager() {
        pagerAdapter = ViewPagerAdapter(this)
        binding.pager.adapter = pagerAdapter
    }

    /**
     *  Adapter to handle view pager
     */
    class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount(): Int {
            return 4
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                1 -> HomeFragment.newInstance("now_playing")
                2 -> HomeFragment.newInstance("popular")
                3 -> HomeFragment.newInstance("top_rated")
                else -> HomeFragment.newInstance("upcoming")
            }
        }
    }
}