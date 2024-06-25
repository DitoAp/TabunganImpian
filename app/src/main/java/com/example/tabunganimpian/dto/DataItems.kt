package com.example.tabunganimpian.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class DataItems: Serializable {
    @field:SerializedName("id")
    val idItem: String? = null

    @field:SerializedName("tabungan_id")
    val tabungan_id: String? = null

    @field:SerializedName("category")
    val category: String? = null

    @field:SerializedName("currency")
    val currency: String? = null

    @field:SerializedName("created_at")
    val created_at: String? = null
}