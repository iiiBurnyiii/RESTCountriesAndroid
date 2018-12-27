package com.example.countries.data.db

import androidx.paging.DataSource
import androidx.room.Transaction
import com.example.countries.data.db.entity.JoinEntity
import com.example.countries.domain.CountryDTO
import com.example.countries.model.CountryDetails
import com.example.countries.model.CountryTitle
import com.example.countries.util.LoadState
import com.example.countries.util.SingleLiveEvent
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.Singles
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class DatabaseHelper @Inject constructor(
    val db: CountriesDatabase) {

    fun handleData(data: List<CountryDTO>,
                   needRefresh: Boolean,
                   loadState: SingleLiveEvent<LoadState>): Disposable {

        loadState.postValue(LoadState.LOADING)

       return Completable.fromAction {
            when (needRefresh) {
                true -> update(data)
                false -> insert(data)
            }
        }.subscribeOn(Schedulers.io())
            .subscribeBy(
                onComplete = {
                    loadState.postValue(LoadState.LOADED.apply {
                        msg = "Data inserted to database." })
                },
                onError = {
                    loadState.postValue(LoadState.ERROR.apply {
                        msg = "Unable to insert body to database." })
                }
            )
    }


    @Transaction
    fun getCountryDetails(alphaCode: String): Single<CountryDetails> =
        Singles.zip(
            db.countryDao().getCountryByAlphaCode(alphaCode),
            db.languageDao().getLanguagesByAlphaCode(alphaCode),
            db.currencyDao().getCurrenciesByAlphaCode(alphaCode),
            db.timezoneDao().getTimezonesByAlphaCode(alphaCode))
        { country, languages, currencies, timezones->
            val alphaCodeWithName = "${country.alphaCode}, ${country.name}"

            CountryDetails(alphaCodeWithName, country.flagUri,
                languages.map { it.toCommonModel() },
                currencies.map { it.toCommonModel() },
                timezones.map { it.toCommonModel() })
        }

    fun getCountries(): DataSource.Factory<Int, CountryTitle> =
            db.countryDao().getCountries().map {
                CountryTitle(it.alphaCode, it.name, it.flagUri)
            }

    @Transaction
    private fun insert(data: List<CountryDTO>){
        data.forEach { country ->
            db.countryDao().insert(country.toEntity())
            db.languageDao().insert(country.languages.map { it.toEntity() })
            db.currencyDao().insert(country.currencies.map { it.toEntity() })
            db.timezoneDao().insert(country.timezones.map { it.toEntity() })

            val languages = country.languages
            val currencies = country.currencies
            val timezones = country.timezones

            val times = maxOf<Int>(languages.size,
                currencies.size, timezones.size) - 1
            for (i in 0..times) {
                db.joinDao().insert(
                    JoinEntity(
                        countryAlphaCode = country.alphaCode,
                        languageCode = languages.elementAtOrNull(i)?.iso639,
                        currencyCode = currencies.elementAtOrNull(i)?.code,
                        timezoneCode = timezones.elementAtOrNull(i)?.timezone
                    )
                )
            }
        }
    }

    @Transaction
    private fun update(data: List<CountryDTO>) {
        data.forEach { dto ->
            db.countryDao().update(dto.toEntity())
            db.languageDao().update(dto.languages.map { it.toEntity() })
            db.currencyDao().update(dto.currencies.map { it.toEntity() })
            db.timezoneDao().update(dto.timezones.map { it.toEntity() })
        }
    }

}