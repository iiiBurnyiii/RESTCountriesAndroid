package com.example.countries.data

import androidx.paging.PagedList
import com.example.countries.model.Country

class CountryListBoundaryCallback(
    private val loadData: (Boolean) -> Unit
) : PagedList.BoundaryCallback<Country>() {

    override fun onZeroItemsLoaded() {
        loadData.invoke(false)
    }

}
