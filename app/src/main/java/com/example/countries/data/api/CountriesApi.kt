package com.example.countries.data.api

import android.util.Log
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface CountriesApi {

    @GET("all?fields=name;flag;currencies;languages;timezones;alpha3Code")
    fun loadCountriesWithFilter(): Single<List<CountryResponse>?>

    companion object {
        private const val BASE_URL = "https://restcountries.eu/rest/v2/"

        fun create(): CountriesApi {
            val logger =
                HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                    Log.d("ApiLogger", it)
                }).apply {
                    level = HttpLoggingInterceptor.Level.BASIC
                }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CountriesApi::class.java)
        }

    }

}