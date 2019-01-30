package com.andresako.rocketx.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import com.andresako.rocketx.R
import com.andresako.rocketx.data.NetworkStatus
import com.andresako.rocketx.data.Status
import com.andresako.rocketx.ui.rocket.RocketListFragment
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : DaggerAppCompatActivity(), MainCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        if (savedInstanceState == null) {
            val fragment = RocketListFragment()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.nav_host_fragment, fragment)
                .commit()
        }
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

    override fun setTitle(title: String) {
        val enabled = supportFragmentManager.backStackEntryCount > 0
        supportActionBar?.setDisplayHomeAsUpEnabled(enabled)
        if (enabled) toolbar.title = title
        else toolbar.title = getString(R.string.app_name)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                setTitle("")
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

interface MainCallback {
    fun goToFragment(fragment: Fragment)

    fun updateState(networkState: NetworkStatus)

    fun setTitle(title: String)
}
