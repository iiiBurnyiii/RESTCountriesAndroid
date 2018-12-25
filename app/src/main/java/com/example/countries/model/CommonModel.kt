package com.example.countries.model

data class CommonModel(
    val fieldOne: String = "none",
    val fieldTwo: String = "none",
    val fieldThree: String = "none"
) {
    fun fieldIsNone(field: String): Boolean = field == "none"
}