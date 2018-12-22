package com.example.countries.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.countries.model.Country

interface IRepository {

    fun loadCountries(needRefresh: Boolean)

    fun getCountries(listPageSize: Int): LiveData<PagedList<Country>>

    fun getCountry(alphaCode: String)

    fun clear(viewModel: ViewModel)

}
