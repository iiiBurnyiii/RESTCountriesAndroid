package com.example.countries.ui

import android.os.Bundle
import com.example.countries.R
import com.example.countries.ui.countryList.CountryListFragment
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container, CountryListFragment.newInstance())
                .commitNow()
        }
    }

}
