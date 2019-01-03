package com.example.countries.domain

import androidx.paging.PagedList
import com.example.countries.model.CountryTitle

class CountryListBoundaryCallback(
    private val loadCountries: () -> Unit
) : PagedList.BoundaryCallback<CountryTitle>() {

    override fun onZeroItemsLoaded() {
        loadCountries.invoke()
    }

}
