package com.example.countries.ui.bindingAdapters

import androidx.databinding.BindingAdapter
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.example.countries.model.CommonModel
import com.example.countries.model.CountryTitle
import com.example.countries.ui.country.CommonAdapter
import com.example.countries.ui.countryList.CountryPagedListAdapter

object ListBindings {

    @JvmStatic
    @BindingAdapter("items")
    fun RecyclerView.setItems(items: PagedList<CountryTitle>?) {
        items?.let {
            with(this.adapter as CountryPagedListAdapter) {
                submitList(items)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("items")
    fun RecyclerView.setItems(items: List<CommonModel>?) {
        items?.let {
            with(this.adapter as CommonAdapter) {
                submitList(items)
            }
        }
    }

}