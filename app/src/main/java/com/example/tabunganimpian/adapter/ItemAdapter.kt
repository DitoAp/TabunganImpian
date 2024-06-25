package com.example.tabunganimpian.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tabunganimpian.databinding.ItemsDataBinding
import com.example.tabunganimpian.dto.DataItems
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

class ItemAdapter() : RecyclerView.Adapter<ItemAdapter.ItemHolder>()  {

    var itemsData = mutableListOf <DataItems>()
    private lateinit var binding: ItemsDataBinding

    inner class ItemHolder(binding: ItemsDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: DataItems) {
            binding.date.text = setStringDate(item.created_at.toString())



            binding.value.text = item.currency
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        binding = ItemsDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemsData.size ?: 0
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.onBind(itemsData[position])
    }

    fun setStringDate(date: String) : String {
        val inputFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val outputFormatter = SimpleDateFormat("dd-MM-yyyy HH:mm")
        val parsed = inputFormatter.parse(date)
        val formatted = outputFormatter.format(parsed)
        return formatted
    }
}