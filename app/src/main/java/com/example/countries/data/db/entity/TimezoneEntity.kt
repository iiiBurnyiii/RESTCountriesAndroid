package com.example.countries.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.countries.model.CommonModel

@Entity(tableName = "timezones")
data class TimezoneEntity(
    @PrimaryKey
    val timezone: String
) : IEntity {

    override fun toCommonModel(): CommonModel =
        CommonModel(timezone)

}