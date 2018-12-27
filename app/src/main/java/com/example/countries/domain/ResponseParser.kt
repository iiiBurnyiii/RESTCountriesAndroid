package com.example.countries.domain

import com.example.countries.data.api.CountryResponse
import com.example.countries.data.api.CurrencyResponse
import com.example.countries.data.api.LanguageResponse
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class ResponseParser(
    private val response: List<CountryResponse>
) {

    fun parse(
        doSomeWithResult: (List<CountryDTO>) -> Unit,
        doSomeWithError: (Throwable) -> Unit): Disposable =
        parseResponse(response)
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = { dtoList ->
                    doSomeWithResult(dtoList)
                },
                onError = { e ->
                    doSomeWithError(e)
                }
            )


    private fun parseResponse(response: List<CountryResponse>): Single<List<CountryDTO>> =
        Single.create { emitter ->
            val dtoList: MutableList<CountryDTO> = mutableListOf()

            response.forEach {
                dtoList.add(parseCountry(it))
            }

            emitter.onSuccess(dtoList)
        }

    private fun parseCountry(country: CountryResponse): CountryDTO =
        CountryDTO(
            alphaCode = country.alpha3Code,
            name = country.name,
            flagUri = country.flagUrl,
            languages = parseLanguages(country.languages),
            currencies = parseCurrencies(country.currencies),
            timezones = parseTimezones(country.timezones)
        )

    private fun parseLanguages(languages: List<LanguageResponse>): List<LanguageDTO> =
        languages.map {
            LanguageDTO(
                iso639 = it.iso639,
                name = it.name,
                nativeName = it.nativeName
            )
        }

    private fun parseCurrencies(currencies: List<CurrencyResponse>): List<CurrencyDTO> =
        currencies.filter { it.name != "[D]" }.map {
            CurrencyDTO(
                code = it.code ?: "none",
                name = it.name ?: "none",
                symbol = it.symbol ?: "none"
            )
        }

    private fun parseTimezones(timezones: List<String>): List<TimezoneDTO> =
            timezones.map {
                TimezoneDTO(timezone = it)
            }

}