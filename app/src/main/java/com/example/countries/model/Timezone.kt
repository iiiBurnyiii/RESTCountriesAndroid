package com.example.countries.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.countries.ui.country.ICommonModel

@Entity(tableName = "timezones")
data class Timezone(
    @PrimaryKey
    val timezone: String
) : ICommonModel {
    override fun toCommonModel(): CommonModel =
        CommonModel(timezone)
}