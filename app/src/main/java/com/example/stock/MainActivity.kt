package com.example.stock


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.stock.ui.Helper
import com.example.stock.ui.SearchFragment


class MainActivity : AppCompatActivity(R.layout.activity_main) , Helper {
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController:NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<SearchFragment>(R.id.fragment_search)
            }
        }
         navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
         navController = navHostFragment.navController

    }

    override fun onBackPressed() {
        super.onBackPressed()
        navController.navigateUp()


    }
    override fun goToNext() {

        if (navController.currentDestination?.id == R.id.stocksFragment2) {
            navController.navigate(R.id.action_stocksFragment2_to_suggestionsFragment)
        }

    }

    override fun goBack() {
        if (navController.currentDestination?.id == R.id.suggestionsFragment) {
            navController.navigateUp()
        }
    }


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//
//
////        searh_field.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
////            if (hasFocus) {
////                stocks_text.visibility = View.GONE
////                favourite_text.visibility = View.GONE
////                navController.navigate(R.id.action_stocksFragment_to_suggestionsFragment)
////            } else {
////                //lost focus
////                Log.d("lost" , "lost")
////            }
////        }
//
//
//
//}
    }

