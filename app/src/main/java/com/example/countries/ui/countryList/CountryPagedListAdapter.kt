package com.example.countries.ui.countryList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.countries.databinding.CountryListItemBinding
import com.example.countries.model.Country

class CountryPagedListAdapter (
    val viewModel: CountriesViewModel
) : PagedListAdapter<Country, CountryPagedListAdapter.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CountryListItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, viewModel)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Country>() {
            override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean =
                oldItem.alphaCode == newItem.alphaCode

            override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean =
                    oldItem == newItem
        }
    }

    inner class ViewHolder(
        private val binding: CountryListItemBinding,
        private val viewModel: CountriesViewModel
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(country: Country?) {
            binding.apply {
                this.country = country
                listener =  object : OnCountryClickListener {
                    override fun onClick(country: Country) {
                        viewModel.countryClickEvent.value =
                                country.alphaCode
                    }
                }

                executePendingBindings()
            }
        }

    }

}