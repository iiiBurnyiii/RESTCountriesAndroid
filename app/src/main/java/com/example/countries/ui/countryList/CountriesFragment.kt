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
import com.example.countries.util.ViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.country_list_fragment.*
import javax.inject.Inject

class CountriesFragment : DaggerFragment() {

    private lateinit var binding: CountryListFragmentBinding

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
            initAdapter(this)

            countryClickEvent.observe(this@CountriesFragment, Observer { alphaCode ->
                (activity as MainActivity).openCountryFragment(alphaCode)
            })

            (activity as MainActivity).observeLoadMessages(this.loadState)
        }
    }

    private fun initAdapter(viewModel: CountriesViewModel) {
        with(rvCountryList) {
            layoutManager = LinearLayoutManager(this@CountriesFragment.context)
            adapter = CountryPagedListAdapter(viewModel)
        }
    }

    companion object {
        fun newInstance() = CountriesFragment()
    }

}
