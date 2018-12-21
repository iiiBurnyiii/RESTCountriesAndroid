package com.example.countries.ui.country

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.example.countries.databinding.CountryFragmentBinding
import com.example.countries.util.ViewModelFactory
import dagger.android.support.DaggerFragment
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

        binding.viewModel?.apply {
            loadCountry(countryAlphaCode)
        }
    }

    companion object {
        fun newInstance(alphaCode: String): CountryFragment = CountryFragment().apply {
            countryAlphaCode = alphaCode
        }
    }

}