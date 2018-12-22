package com.example.countries.model

data class CommonItem(
    var fieldOne: String = "none",
    var fieldTwo: String = "none",
    var fieldThree: String = "none"
) {
    fun fieldIsNone(field: String): Boolean = field == "none"
}

interface ICommonItem {

    fun toCommonItem(): CommonItem

}