package com.github.aimanzaki.dotaheroes.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.github.aimanzaki.dotaheroes.R
import dagger.hilt.android.AndroidEntryPoint
import com.github.aimanzaki.dotaheroes.databinding.ActivityMainBinding
import timber.log.Timber


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
      setContentView(binding.root)

      val navHostFragment: NavHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
      val navController: NavController = navHostFragment.navController

      val appBarConfiguration = AppBarConfiguration(navController.graph)
      binding.toolbar.setupWithNavController(navController,appBarConfiguration)

    }
}
