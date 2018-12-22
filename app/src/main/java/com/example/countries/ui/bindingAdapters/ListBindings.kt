package com.example.countries.ui.bindingAdapters

import androidx.databinding.BindingAdapter
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.example.countries.model.Country
import com.example.countries.model.ICommonItem
import com.example.countries.ui.country.CommonAdapter
import com.example.countries.ui.countryList.CountryPagedListAdapter

object ListBindings {

    @JvmStatic
    @BindingAdapter("items")
    fun RecyclerView.setItems(items: PagedList<Country>?) {
        items?.let {
            with(this.adapter as CountryPagedListAdapter) {
                submitList(items)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("items")
    fun RecyclerView.setItems(items: List<ICommonItem>?) {
        items?.let {
            with(this.adapter as CommonAdapter) {
                submitList(items)
            }
        }
    }

}