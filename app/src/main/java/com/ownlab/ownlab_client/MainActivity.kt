package com.ownlab.ownlab_client

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.ownlab.ownlab_client.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding= ActivityMainBinding.inflate(layoutInflater)

        setupToolbar()
        setupNav()
        setContentView(binding?.root)
    }

    override fun onSupportNavigateUp(): Boolean {
        return binding.hostFragment.getFragment<NavHostFragment>().navController.navigateUp()||super.onSupportNavigateUp()
    }

    private fun setupToolbar() {
        val navController = binding.hostFragment.getFragment<NavHostFragment>().navController
        val toolbar = binding.fragmentToolbar

        toolbar.setBackgroundResource(R.color.blue)
        toolbar.setTitleTextColor(Color.WHITE)
        setSupportActionBar(toolbar)

        setupActionBarWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.mainFragment -> toolbar.navigationIcon = null
                R.id.boardFragment -> toolbar.navigationIcon = null
                else -> hideBottomNav()
            }
        }
    }

    private fun setupNav() {
        val navController = binding.hostFragment.getFragment<NavHostFragment>().navController

        binding.bottomNavi.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment -> hideBottomNav()
                R.id.mainFragment -> showBottomNav()
                R.id.boardFragment -> showBottomNav()
                else -> hideBottomNav()
            }
        }
    }

    private fun showBottomNav() {
        binding.bottomNavi.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        binding.bottomNavi.visibility = View.GONE
    }
}