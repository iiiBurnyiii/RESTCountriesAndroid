package com.example.countries.ui.country

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.countries.databinding.CommonItemBinding
import com.example.countries.model.*

class CommonAdapter : RecyclerView.Adapter<CommonAdapter.CommonViewHolder>() {

    private var items: List<CommonItem> = emptyList()

    fun setItems(list: List<ICommonItem>) {
        items = list.map {
            Log.d("CommonAdapterLogger", "item: $it")
            it.toCommonItem()
        }
//        Log.d("CommonAdapterLogger", "items: $items")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CommonItemBinding.inflate(layoutInflater, parent, false)
        return CommonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommonViewHolder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount(): Int =
        items.size


    inner class CommonViewHolder(
        private val binding: CommonItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(commonItem: CommonItem) {
            with(binding) {
                item = commonItem
                Log.d("CommonAdapterLogger", "commonItem: $commonItem")
                executePendingBindings()
            }
        }

    }

}