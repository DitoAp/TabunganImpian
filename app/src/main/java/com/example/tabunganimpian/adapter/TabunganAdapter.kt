package com.example.tabunganimpian.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Adapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tabunganimpian.GlideApp
import com.example.tabunganimpian.databinding.ItemDataBinding
import com.example.tabunganimpian.dto.TabunganItem
import com.example.tabunganimpian.network.ApiService
import com.example.tabunganimpian.network.NetworkConfig

class TabunganAdapter(): RecyclerView.Adapter<TabunganAdapter.TabunganHolder>() {
    var data = mutableListOf<TabunganItem>()
    private lateinit var binding: ItemDataBinding
    private var onClickListener: OnClickListener? = null
    inner class TabunganHolder(binding: ItemDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(get: TabunganItem) {
            binding.title.text = get?.titleTabungan
            binding.target.text = get?.target
            binding.currency.text = get?.savings
            binding.category.text = get?.tipe
            GlideApp.with(binding.image.context)
                .load("http://67.67.67.150:8000"+get?.image)
                .into(binding.image)
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
        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, data[position])
            }
        }
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int, model: TabunganItem)
    }
}