package com.example.countries.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.countries.ui.countryList.CountryListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CountryListFragment.newInstance())
                .commitNow()
        }
    }

}
