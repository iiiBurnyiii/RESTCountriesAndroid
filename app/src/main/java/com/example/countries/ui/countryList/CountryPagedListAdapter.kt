package com.example.countries.ui.countryList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.countries.databinding.CountryListItemBinding
import com.example.countries.model.CountryTitle

class CountryPagedListAdapter (
    val viewModel: CountriesViewModel
) : PagedListAdapter<CountryTitle, CountryPagedListAdapter.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CountryListItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, viewModel)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<CountryTitle>() {
            override fun areItemsTheSame(oldItem: CountryTitle, newItem: CountryTitle): Boolean =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: CountryTitle, newItem: CountryTitle): Boolean =
                    oldItem == newItem
        }
    }

    inner class ViewHolder(
        private val binding: CountryListItemBinding,
        private val viewModel: CountriesViewModel
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(country: CountryTitle?) {
            binding.apply {
                this.country = country
                listener =  object : OnCountryClickListener {
                    override fun onClick(country: CountryTitle) {
                        viewModel.countryClickEvent.value =
                                country.alphaCode
                    }
                }

                executePendingBindings()
            }
        }

    }

}