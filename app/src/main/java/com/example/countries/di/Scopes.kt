package com.example.countries.di

import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class CountryListScope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class CountryScope