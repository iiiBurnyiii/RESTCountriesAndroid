package com.example.countries.ui.countryList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.countries.databinding.CountryListFragmentBinding
import com.example.countries.ui.MainActivity
import com.example.countries.ui.ViewModelFactory
import com.example.countries.util.SingleLiveEvent
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.country_list_fragment.*
import javax.inject.Inject

class CountriesFragment : DaggerFragment() {

    private lateinit var binding: CountryListFragmentBinding
    private lateinit var countriesAdapter: CountryPagedListAdapter
    @Inject lateinit var factory: ViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        binding = CountryListFragmentBinding.inflate(inflater,
            container, false).apply {
            viewModel = ViewModelProviders.of(this@CountriesFragment, factory)[CountriesViewModel::class.java]
            setLifecycleOwner(this@CountriesFragment)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel?.apply {
            countriesAdapter = CountryPagedListAdapter(this)
            initAdapter()
            observeItemChanges(changeItemEvent)
            observeClickEvents(countryClickEvent)
            observeSnackbarMessageEvents(snackbarMessageEvent)
        }
    }

    private fun initAdapter() {
        with(rvCountryList) {
            layoutManager = LinearLayoutManager(this@CountriesFragment.context)
            adapter = countriesAdapter
        }
    }

    private fun observeItemChanges(changeItemEvent: SingleLiveEvent<Int>) {
        changeItemEvent.observe(this, Observer {
            countriesAdapter.notifyItemChanged(it)
        })
    }

    private fun observeClickEvents(clickEvent: SingleLiveEvent<String>) {
        clickEvent.observe(this@CountriesFragment, Observer { alphaCode ->
            (activity as MainActivity).openCountryFragment(alphaCode)
        })
    }

    private fun observeSnackbarMessageEvents(messageEvent: SingleLiveEvent<String>) {
        messageEvent.observe(this, Observer { message ->
            Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
        })
    }

    companion object {
        fun newInstance() = CountriesFragment()
    }

}
