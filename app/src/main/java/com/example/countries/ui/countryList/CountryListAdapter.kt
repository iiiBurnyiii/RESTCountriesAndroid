package com.example.countries.ui.countryList

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.MainThread
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.countries.databinding.CountryItemBinding
import com.example.countries.di.CountryListScope
import com.example.countries.model.Country
import io.reactivex.Observable
import javax.inject.Inject

class CountryListAdapter (
    val viewModel: CountryListViewModel
) : PagedListAdapter<Country, CountryListAdapter.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CountryItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
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

    inner class ViewHolder(
        private val binding: CountryItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(country: Country?) {
            with(binding) {
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

}