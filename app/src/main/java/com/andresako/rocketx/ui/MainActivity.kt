package com.andresako.rocketx.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI
import com.andresako.rocketx.R
import com.andresako.rocketx.data.NetworkStatus
import com.andresako.rocketx.data.Status
import com.andresako.rocketx.ui.rocket.RocketListFragment
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : DaggerAppCompatActivity(), MainCallback {


    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

//        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
//        NavigationUI.setupActionBarWithNavController(this, navController)

        if (savedInstanceState == null) {
            val fragment = RocketListFragment()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.nav_host_fragment, fragment)
                .commit()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }

    override fun goToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .addToBackStack(fragment.tag)
            .commit()
    }

    override fun updateState(networkState: NetworkStatus) {
        when (networkState.status) {
            Status.RUNNING -> showProgress()
            Status.DONE -> hideProgress()
            Status.FAILED -> hideProgress()
        }
    }

    private fun showProgress() {
        appProgress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        appProgress.visibility = View.INVISIBLE
    }

}

interface MainCallback {
    fun goToFragment(fragment: Fragment)

    fun updateState(networkState: NetworkStatus)
}
