package com.example.countries.ui.countryList

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.countries.databinding.CountryListFragmentBinding
import com.example.countries.util.ViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.country_list_fragment.*
import javax.inject.Inject

class CountryListFragment : DaggerFragment() {

    private lateinit var binding: CountryListFragmentBinding

    @Inject lateinit var countryListAdapter: CountryListAdapter
    @Inject lateinit var factory: ViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        binding = CountryListFragmentBinding.inflate(inflater,
            container, false).apply {
            viewModel = ViewModelProviders.of(this@CountryListFragment, factory)[CountryListViewModel::class.java]
            setLifecycleOwner(this@CountryListFragment)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initAdapter()
    }

    private fun initAdapter() {
        with(rvCountryList) {
            layoutManager = LinearLayoutManager(this@CountryListFragment.context)
            adapter = countryListAdapter
        }
    }

    companion object {
        fun newInstance() = CountryListFragment()
    }

}
