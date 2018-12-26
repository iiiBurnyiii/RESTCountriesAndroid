package com.example.countries.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.countries.model.Country
import com.example.countries.model.CountryDetails

interface IRepository {

    fun loadCountries(needRefresh: Boolean)

    fun getCountries(listPageSize: Int): LiveData<PagedList<Country>>

    fun getCountry(alphaCode: String): LiveData<CountryDetails>

    fun clear(viewModel: ViewModel)

}
