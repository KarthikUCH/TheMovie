package com.raju.kvr.themovie.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.raju.kvr.themovie.R
import com.raju.kvr.themovie.databinding.FragmentMainBinding
import com.raju.kvr.themovie.ui.home.HomeFragment


/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {

    private lateinit var pagerAdapter: ViewPagerAdapter

    private lateinit var binding: FragmentMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayHomePage()
    }


    private fun displayHomePage() {
        initViewPager()
        initTabLayout()
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
        pagerAdapter = ViewPagerAdapter(this.requireActivity())
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