package com.example.app

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.app.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private var previousMenuItem: MenuItem? = null

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.hotels_app_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.filterByDistanceFromCenter -> {
                handleItemClick(item, "filterByDistance")
                true
            }

            R.id.filterBySuitesAvailability -> {
                handleItemClick(item, "filterBySuitesAvailability")
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun handleItemClick(item: MenuItem, bundleKey: String) {
        if (previousMenuItem != null) {
            previousMenuItem!!.isEnabled = true
        }
        previousMenuItem = item
        item.isChecked = !item.isChecked
        item.isEnabled = false
        supportFragmentManager.setFragmentResult(
            "filterOptionsRequestKey",
            bundleOf(bundleKey to !item.isEnabled)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.hotelsAppBar)

        setupNavigation()

        setSupportActionBar(binding.hotelsAppBar)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        navController = navHostFragment.navController
        val inflater = navController.navInflater
        val graph = inflater.inflate(R.navigation.main_nav_graph)
        navController.graph = graph
        setupActionBarWithNavController(navController)
    }
}