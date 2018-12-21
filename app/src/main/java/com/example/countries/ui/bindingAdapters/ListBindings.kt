package com.example.countries.ui.bindingAdapters

import androidx.databinding.BindingAdapter
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.example.countries.model.Country
import com.example.countries.ui.countryList.CountryListAdapter

object ListBindings {

    @JvmStatic
    @BindingAdapter("items")
    fun RecyclerView.submitList(items: PagedList<Country>?) {
        items?.let {
            with(this.adapter as CountryListAdapter) {
                submitList(items)
            }
        }
    }

}