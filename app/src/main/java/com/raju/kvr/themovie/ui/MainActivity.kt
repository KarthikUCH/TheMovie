package com.raju.kvr.themovie.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.raju.kvr.themovie.R
import com.raju.kvr.themovie.databinding.ActivityMainBinding
import com.raju.kvr.themovie.ui.favourite.FavouriteActivity
import com.raju.kvr.themovie.ui.search.SearchActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpNavController()

        observeViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                startActivity(Intent(this, SearchActivity::class.java))
                true
            }

            R.id.action_favourite -> {
                startActivity(Intent(this, FavouriteActivity::class.java))
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun setUpNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        navController.addOnDestinationChangedListener { controller, destination, arguments ->

            if (destination.label?.equals("The Movie") == true) {
                setUpActionBar()
            }

            println("The destination is ${destination.label} ")
        }
    }

    private fun setUpActionBar() {
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController)
        supportActionBar?.show()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.status.collect {
                    when (it) {
                        MainViewModel.Status.LOADING -> {
                            displayLoading()
                        }

                        MainViewModel.Status.SUCCESS -> {
                            mainViewModel.displayedHomePage()
                        }

                        MainViewModel.Status.ERROR -> {
                            displayError()
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    private fun displayLoading() {
        binding.apply {
            imgViewStatus.setImageResource(R.drawable.loading_animation)
            txtViewStatus.text = resources.getString(R.string.status_loading)
        }
    }

    private fun displayError() {
        binding.apply {
            imgViewStatus.setImageResource(R.drawable.ic_baseline_error_outline_24)
            txtViewStatus.text = resources.getString(R.string.status_Error)
        }
    }

}