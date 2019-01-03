package com.example.countries.domain

import com.example.countries.data.api.CountryResponse
import com.example.countries.data.api.CurrencyResponse
import com.example.countries.data.api.LanguageResponse
import io.reactivex.Single

class ResponseTransformer(
    private val response: List<CountryResponse>
) {

    fun transform(): Single<List<CountryDTO>> =
        Single.create { emitter ->
            val dtoList: MutableList<CountryDTO> = mutableListOf()

            response.forEach {
                dtoList.add(transformCountry(it))
            }

            emitter.onSuccess(dtoList)
        }

    private fun transformCountry(country: CountryResponse): CountryDTO {
        val flagUrl = country.flagUrl
        flagUrlList.add(flagUrl)
        return CountryDTO(
            alphaCode = country.alpha3Code,
            name = country.name,
            flagName = flagUrl.substring(flagUrl.lastIndexOf("/") + 1),
            languages = transformLanguages(country.languages),
            currencies = transformCurrencies(country.currencies),
            timezones = transformTimezones(country.timezones)
        )
    }

    private fun transformLanguages(languages: List<LanguageResponse>): List<LanguageDTO> =
        languages.map {
            LanguageDTO(
                iso639 = it.iso639,
                name = it.name,
                nativeName = it.nativeName
            )
        }

    private fun transformCurrencies(currencies: List<CurrencyResponse>): List<CurrencyDTO> =
        currencies.filter { it.name != "[D]" }.map {
            CurrencyDTO(
                code = it.code ?: "none",
                name = it.name ?: "none",
                symbol = it.symbol ?: "none"
            )
        }

    private fun transformTimezones(timezones: List<String>): List<TimezoneDTO> =
            timezones.map {
                TimezoneDTO(timezone = it)
            }

    companion object {
        private val flagUrlList: MutableList<String> = mutableListOf()

        fun getFlagUrlList(): List<String> =
            flagUrlList.toList()
    }

}