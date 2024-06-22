package com.example.tabunganimpian.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Adapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tabunganimpian.databinding.ItemDataBinding
import com.example.tabunganimpian.dto.TabunganItem

class TabunganAdapter(): RecyclerView.Adapter<TabunganAdapter.TabunganHolder>() {
    var data = mutableListOf<TabunganItem>()
    private lateinit var binding: ItemDataBinding
    inner class TabunganHolder(binding: ItemDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(get: TabunganItem) {
            binding.title.text = get?.titleTabungan
            binding.target.text = get?.target
            binding.currency.text = get?.total
            binding.category.text = get?.tipe
//            binding.image.setImageBitmap()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabunganHolder {
        binding = ItemDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TabunganHolder(binding)
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: TabunganHolder, position: Int) {
        holder.onBind(data[position])
    }
}