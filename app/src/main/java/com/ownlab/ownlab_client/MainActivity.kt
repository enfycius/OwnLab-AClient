package com.ownlab.ownlab_client

import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.ownlab.ownlab_client.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding= ActivityMainBinding.inflate(layoutInflater)

        val toolbar = binding.fragmentToolbar

        toolbar.setBackgroundResource(R.color.blue)
        toolbar.setTitleTextColor(Color.WHITE)
        setSupportActionBar(toolbar)

        setupActionBarWithNavController(binding.hostFragment.getFragment<NavHostFragment>().navController)
        setContentView(binding?.root)
    }

    override fun onSupportNavigateUp(): Boolean {
        return binding.hostFragment.getFragment<NavHostFragment>().navController.navigateUp()||super.onSupportNavigateUp()
    }
}