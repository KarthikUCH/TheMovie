package com.raju.kvr.themovie.ui.search

import android.os.Bundle
import android.view.ContextMenu
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import com.raju.kvr.themovie.R
import com.raju.kvr.themovie.databinding.FragmentFavouriteMovieBinding
import com.raju.kvr.themovie.databinding.FragmentSearchMovieBinding
import com.raju.kvr.themovie.ui.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * Use the [SearchMovieFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class SearchMovieFragment : Fragment() {
    private lateinit var binding: FragmentSearchMovieBinding

    private lateinit var searchView: SearchView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideMainActionBar()
        setUpActionBar()
    }

    private fun hideMainActionBar() {
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    private fun setUpActionBar() {
        binding.toolbar.setNavigationIcon(R.drawable.arrow_back)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        binding.toolbar.title = ""
        binding.toolbar.inflateMenu(R.menu.search_menu)
        searchView = binding.toolbar.menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                performSearch(searchView.query.toString())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        searchView.onActionViewExpanded()
    }

    private fun performSearch(query: String) {
        if (query.isBlank()) return

        val fragment = HomeFragment.newInstance(query, true)
        val ft = childFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_container_view, fragment)
        ft.commit();
    }
}