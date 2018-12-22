package com.example.countries.ui.country

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.countries.databinding.CountryFragmentBinding
import com.example.countries.util.ViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.country_fragment.*
import javax.inject.Inject

class CountryFragment : DaggerFragment() {

    private lateinit var binding: CountryFragmentBinding
    private lateinit var countryAlphaCode: String

    @Inject lateinit var factory: ViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = CountryFragmentBinding.inflate(inflater, container,
            false).apply {
            viewModel = ViewModelProviders.of(this@CountryFragment, factory)[CountryViewModel::class.java]
            setLifecycleOwner(this@CountryFragment)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initAdapter(
            rvLanguages,
            rvCurrencies,
            rvTimezones
        )

//        rvLanguages.apply {
//            layoutManager = LinearLayoutManager(context)
//            adapter = CommonAdapter()
//        }
//        rvCurrencies.apply {
//            layoutManager = LinearLayoutManager(context)
//            adapter = CommonAdapter()
//        }
//        rvTimezones.apply {
//            layoutManager = LinearLayoutManager(context)
//            adapter = CommonAdapter()
//        }

        binding.viewModel?.apply {
            loadCountry(countryAlphaCode)
        }
    }

    private fun initAdapter(vararg recyclerViews: RecyclerView) {
        recyclerViews.forEach { it ->
            with(it) {
                layoutManager = LinearLayoutManager(context)
                adapter = CommonAdapter()
            }
        }
    }


    companion object {
        fun newInstance(alphaCode: String): CountryFragment = CountryFragment().apply {
            countryAlphaCode = alphaCode
        }
    }

}