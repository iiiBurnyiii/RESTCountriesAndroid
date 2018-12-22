package com.example.countries.ui.countryList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.countries.databinding.CountryListItemBinding
import com.example.countries.model.Country

class CountryPagedListAdapter (
    val viewModel: CountryListViewModel
) : PagedListAdapter<Country, CountryListViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CountryListItemBinding.inflate(inflater, parent, false)
        return CountryListViewHolder(binding, viewModel)
    }

    override fun onBindViewHolder(holder: CountryListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Country>() {
            override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean =
                oldItem.countryNameAndFlag.alphaCode == newItem.countryNameAndFlag.alphaCode

            override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean =
                    oldItem == newItem
        }
    }

}