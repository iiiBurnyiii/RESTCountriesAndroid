package com.example.countries.ui.countryList

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.countries.R
import com.example.countries.databinding.CountryListFragmentBinding
import com.example.countries.ui.MainActivity
import com.example.countries.ui.country.CountryFragment
import com.example.countries.util.ViewModelFactory
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
                Log.d("FragmentLogger", "Country onClick, alphaCode: $alphaCode")
                val mActivity = activity as MainActivity
                mActivity.openCountryFragment(alphaCode)
            })
        }
    }

    private fun initAdapter(viewModel: CountryListViewModel) {
        with(rvCountryList) {
            layoutManager = LinearLayoutManager(this@CountryListFragment.context)
            adapter = CountryListAdapter(viewModel)
        }
    }

    private fun openCountryFragment(alphaCode: String) {

    }

    companion object {
        fun newInstance() = CountryListFragment()
    }

}
