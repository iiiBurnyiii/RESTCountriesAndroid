package com.example.countries.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.countries.data.db.entity.CountryTitle
import com.example.countries.model.CountryModel

interface IRepository {

    fun loadCountries(needRefresh: Boolean)

    fun getCountries(listPageSize: Int): LiveData<PagedList<CountryTitle>>

    fun getCountry(alphaCode: String): LiveData<CountryModel>

    fun clear(viewModel: ViewModel)

}
