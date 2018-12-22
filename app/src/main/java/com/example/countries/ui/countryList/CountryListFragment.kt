package com.example.countries.ui.countryList

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.countries.databinding.CountryListFragmentBinding
import com.example.countries.ui.MainActivity
import com.example.countries.util.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.country_list_fragment.*
import javax.inject.Inject

class CountryListFragment : DaggerFragment() {

    private lateinit var binding: CountryListFragmentBinding

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

        binding.viewModel?.apply {
            initAdapter(this)

            countryClickEvent.observe(this@CountryListFragment, Observer { alphaCode ->
                (activity as MainActivity).openCountryFragment(alphaCode)
            })

            observeLoadErrors(this)
        }
    }

    private fun initAdapter(viewModel: CountryListViewModel) {
        with(rvCountryList) {
            layoutManager = LinearLayoutManager(this@CountryListFragment.context)
            adapter = CountryPagedListAdapter(viewModel)
        }
    }

    private fun observeLoadErrors(viewModel: CountryListViewModel) {
        var snackbar: Snackbar
        viewModel.loadState.observe(this, Observer { loadState ->
            loadState.msg?.let {
                snackbar = Snackbar.make(binding.root, loadState.msg!!, Snackbar.LENGTH_SHORT)
                    .apply { show() }
            }
        })
    }

    companion object {
        fun newInstance() = CountryListFragment()
    }

}
