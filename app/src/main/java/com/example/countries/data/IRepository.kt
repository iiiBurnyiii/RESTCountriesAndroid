package com.example.countries.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.countries.model.CountryTitle

interface IRepository {

    fun loadCountries()

    fun getCountries(listPageSize: Int): LiveData<PagedList<CountryTitle>>

    fun getCountry(alphaCode: String)

    fun clear(viewModel: ViewModel)

}
