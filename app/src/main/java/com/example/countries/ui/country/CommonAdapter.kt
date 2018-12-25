package com.example.countries.ui.country

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.countries.databinding.CommonItemBinding
import com.example.countries.model.CommonModel

class CommonAdapter : RecyclerView.Adapter<CommonAdapter.ViewHolder>() {

    private var items: List<CommonModel> = emptyList()

    fun submitList(list: List<ICommonModel>) {
        items = list.map {
            it.toCommonModel()
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CommonItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }


    override fun getItemCount(): Int = items.size


    inner class ViewHolder(
        private val binding: CommonItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(commonModel: CommonModel) {
            with(binding) {
                item = commonModel
                executePendingBindings()
            }
        }

    }

}