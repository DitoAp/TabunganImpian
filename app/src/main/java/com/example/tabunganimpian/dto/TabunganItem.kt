package com.example.tabunganimpian.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class TabunganItem : Serializable{
    @field:SerializedName("name")
    val titleTabungan: String? = null

    @field:SerializedName("id")
    val tabunganId: String? = null

    @field:SerializedName("tipe")
    val tipe: String? = null

    @field:SerializedName("target")
    val target: String? = null

    @field:SerializedName("total")
    val total: String? = null

    @field:SerializedName("menabung")
    val savings: String? = null

    @field:SerializedName("image")
    val image: String? = null

    @field:SerializedName("status")
    val status: String? = null
}
