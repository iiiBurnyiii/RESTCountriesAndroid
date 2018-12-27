package com.example.countries.data.db.entity

import com.example.countries.model.CommonModel

interface IEntity {

    fun toCommonModel(): CommonModel

}