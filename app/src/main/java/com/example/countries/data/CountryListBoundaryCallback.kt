package com.example.countries.data

import androidx.paging.PagedList
import com.example.countries.model.CountryTitle

class CountryListBoundaryCallback(
    private val loadData: () -> Unit
) : PagedList.BoundaryCallback<CountryTitle>() {

    override fun onZeroItemsLoaded() {
        loadData.invoke()
    }

}
