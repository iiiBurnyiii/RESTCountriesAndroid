package com.example.countries.domain

import com.example.countries.model.CountriesResult
import com.example.countries.model.CountryResult

interface IRepository {

    fun getCountries(listPageSize: Int): CountriesResult

    fun getCountry(alphaCode: String): CountryResult

}
