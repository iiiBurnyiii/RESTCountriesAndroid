package com.example.countries.data

import androidx.paging.PagedList
import com.example.countries.data.db.entity.CountryTitle

class CountryListBoundaryCallback(
    private val loadData: (Boolean) -> Unit
) : PagedList.BoundaryCallback<CountryTitle>() {

    override fun onZeroItemsLoaded() {
        loadData.invoke(false)
    }

}
