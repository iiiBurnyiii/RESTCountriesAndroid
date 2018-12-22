package com.example.countries.ui.countryList

import androidx.recyclerview.widget.RecyclerView
import com.example.countries.databinding.CountryListItemBinding
import com.example.countries.model.Country

class CountryListViewHolder(
    private val binding: CountryListItemBinding,
    private val viewModel: CountryListViewModel
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(country: Country?) {
        binding.apply {
            this.country = country
            listener =  object : OnCountryClickListener {
                override fun onClick(country: Country) {
                    viewModel.countryClickEvent.value =
                            country.countryNameAndFlag.alphaCode
                }
            }

            executePendingBindings()
        }
    }

}