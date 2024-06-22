package com.example.tabunganimpian.response

import com.example.tabunganimpian.dto.TabunganItem
import com.google.gson.annotations.SerializedName

data class ResultTabungan(
    @field:SerializedName("data")
    val dataTabungan: List<TabunganItem>? = null
)
