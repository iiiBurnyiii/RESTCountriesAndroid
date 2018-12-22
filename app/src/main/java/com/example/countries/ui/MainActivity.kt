package com.example.countries.ui

import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.countries.R
import com.example.countries.ui.country.CountryFragment
import com.example.countries.ui.countryList.CountryListFragment
import com.example.countries.util.LoadState
import com.example.countries.util.SingleLiveEvent
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.main_activity.*

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

    fun observeLoadMessages(loadStateEvent: SingleLiveEvent<LoadState>) {
        loadStateEvent.observe(this, Observer { loadState ->
            loadState.msg?.let {
                Snackbar.make(container, loadState.msg!!, Snackbar.LENGTH_SHORT).show()
            }
        })
    }

    private fun onBackStackChange() {
        when(fragmentManager.findFragmentById(R.id.container)) {
            is CountryListFragment, null -> false
            else -> true
        }.let { supportActionBar?.setDisplayHomeAsUpEnabled(it) }
    }

}
