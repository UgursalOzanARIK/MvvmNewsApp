package com.ozanarik.MvvmNewsApp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.ozanarik.MvvmNewsApp.R
import com.ozanarik.MvvmNewsApp.data.remote.NewsApi
import com.ozanarik.MvvmNewsApp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    @Inject lateinit var newsApi: NewsApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)


        handleBottomNavigation()
        setContentView(binding.root)

    }


    private fun handleBottomNavigation(){

        val bottomNavBar= binding.bottomNavBar

        val navHostFragment:NavHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment

        NavigationUI.setupWithNavController(bottomNavBar,navHostFragment.navController)





    }





}


