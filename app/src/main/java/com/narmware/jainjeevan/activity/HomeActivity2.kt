package com.narmware.jainjeevan.activity

import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.narmware.jainjeevan.R
import com.narmware.jainjeevan.fragment.AboutFragment
import com.narmware.jainjeevan.fragment.AddVendorFragment
import com.narmware.jainjeevan.fragment.HomeFragment
import com.narmware.jainjeevan.fragment.ProfileFragment
import com.narmware.jainjeevan.support.Support
import kotlinx.android.synthetic.main.activity_home2.*
import kotlinx.android.synthetic.main.app_bar_home2.*

class HomeActivity2 : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, HomeFragment.OnFragmentInteractionListener,
AddVendorFragment.OnFragmentInteractionListener, AboutFragment.OnFragmentInteractionListener, ProfileFragment.OnFragmentInteractionListener{
    val homeFragment: HomeFragment = HomeFragment()
    val addVendorFragment: AddVendorFragment = AddVendorFragment()
    val profileFragment: ProfileFragment = ProfileFragment()
    val aboutFragment: AboutFragment = AboutFragment()

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,fragment)
                .commit()
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container,fragment)
                .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home2)
        setSupportActionBar(toolbar)

        addFragment(homeFragment)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home_activity2, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> { replaceFragment(homeFragment)  }
            R.id.nav_add_vendor -> { replaceFragment(addVendorFragment) }
            R.id.nav_profile -> { replaceFragment(profileFragment) }
            R.id.nav_about -> { replaceFragment(aboutFragment) }
            R.id.nav_share -> { Support.mt(this, "Share Acion") }
            R.id.nav_send -> { Support.mt(this, "Semd action") }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
