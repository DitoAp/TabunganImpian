package com.example.tabunganimpian.response

import com.example.tabunganimpian.dto.DataItems
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ResultItem(
    @field:SerializedName("data")
    val data: List<DataItems>? = null
)