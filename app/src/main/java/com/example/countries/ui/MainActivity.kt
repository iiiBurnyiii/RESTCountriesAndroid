package com.example.countries.ui

import android.os.Bundle
import com.example.countries.R
import com.example.countries.ui.country.CountryFragment
import com.example.countries.ui.countryList.CountryListFragment
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {

    private val fragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        setSupportActionBar(findViewById(R.id.toolbar))

        if (savedInstanceState == null) {
            fragmentManager
                .beginTransaction()
                .add(R.id.container, CountryListFragment.newInstance())
                .commit()
        }

        fragmentManager.addOnBackStackChangedListener {
            onBackStackChange()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onResume() {
        super.onResume()
        onBackStackChange()
    }

    fun openCountryFragment(alphaCode: String) {
        fragmentManager
            .beginTransaction()
            .replace(R.id.container, CountryFragment.newInstance(alphaCode))
            .addToBackStack(null)
            .commit()
    }

    private fun onBackStackChange() {
        when(fragmentManager.findFragmentById(R.id.container)) {
            is CountryListFragment, null -> false
            else -> true
        }.let { supportActionBar?.setDisplayHomeAsUpEnabled(it) }
    }

}
