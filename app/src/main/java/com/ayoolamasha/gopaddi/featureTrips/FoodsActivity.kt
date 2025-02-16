package com.ayoolamasha.gopaddi.featureTrips

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.ayoolamasha.gopaddi.R
import com.ayoolamasha.gopaddi.databinding.ActivityFoodNavHostHolderBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodsActivity : AppCompatActivity(){
    private lateinit var binding: ActivityFoodNavHostHolderBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodNavHostHolderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNav()
        setUpHideAndShow()
        binding.bottomNavMain.labelVisibilityMode = BottomNavigationView.LABEL_VISIBILITY_LABELED
    }

    private fun setupBottomNav() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.dashboard_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNavMain, navHostFragment.navController)
        binding.bottomNavMain.setupWithNavController(navController)
    }

    private fun showBottomNavigation() {
        binding.bottomNavMain.visibility = View.VISIBLE
    }

    private fun hideBottomNavigation() {
        binding.bottomNavMain.visibility = View.GONE
    }

    private fun setUpHideAndShow() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.addFoodFragment -> {
                    hideBottomNavigation()
                }

                else -> {
                    showBottomNavigation()
                }
            }
        }
    }
}