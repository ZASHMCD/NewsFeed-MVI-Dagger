package com.android.myapplication.newsfeed.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.android.myapplication.newsfeed.R
import com.android.myapplication.newsfeed.ui.headlines.HeadlineFragment
import com.android.myapplication.newsfeed.ui.sources.ArticlesSourceFragment
import com.android.myapplication.newsfeed.util.BottomNavController
import com.android.myapplication.newsfeed.util.setUpNavigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : DaggerAppCompatActivity(),
    BottomNavController.NavGraphProvider,
    BottomNavController.OnNavigationGraphChanged,
    BottomNavController.OnNavigationReselectedListener {

    private lateinit var bottomNavigationView: BottomNavigationView
    private val bottomNavController by lazy(LazyThreadSafetyMode.NONE) {
        BottomNavController(
            this,
            R.id.main_nav_host_fragment,
            R.id.nav_headlines,
            this,
            this
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupActionBar()
        bottomNavigationView = findViewById(R.id.bottom_navigation_view)
        bottomNavigationView.setUpNavigation(bottomNavController,this)
        if(savedInstanceState == null){
            bottomNavController.onNavigationItemSelected()
        }
    }

    override fun getNavGraphId(itemId: Int): Int = when (itemId) {
        R.id.nav_headlines -> {
            R.navigation.nav_headlines
        }
        R.id.nav_sources -> {
            R.navigation.nav_sources
        }
        R.id.nav_favorites -> {
            R.navigation.nav_favorites
        }
        else -> {
            R.navigation.nav_headlines
        }
    }

    private fun setupActionBar(){
        setSupportActionBar(toolbar)
    }

    override fun onBackPressed() = bottomNavController.onBackPressed()

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)

    }


    override fun onGraphChange() {
        //what needs to happen when the graph changes?
    }

    override fun onReselectNavItem(navController: NavController, fragment: Fragment) = when(fragment){
        is ArticlesSourceFragment -> {
            navController.navigate(R.id.action_articlesSourceFragment_to_sourcesFragment)
        }
        else->{
            //Nothing need to be done here
        }
    }
}