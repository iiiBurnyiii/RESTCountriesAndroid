package com.example.countries.ui.countryList

import com.example.countries.data.db.entity.CountryTitle

interface OnCountryClickListener {

    fun onClick(country: CountryTitle)

}