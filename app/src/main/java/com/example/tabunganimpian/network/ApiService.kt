package com.example.tabunganimpian.network

import com.example.tabunganimpian.response.ResultStatus
import com.example.tabunganimpian.response.ResultTabungan
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {
    @GET("tabungan")
    fun getDataTabungan(): Call<ResultTabungan>

    @FormUrlEncoded
    @POST("tabungan")
    fun addDataTabungan(@Field("name") name: String?,
                        @Field("tipe") tipe: String?,
                        @Field("target") target: String?,
                        @Field("total") total: String?,
                        @Field("image") image: String?
                        ) : Call<ResultStatus>
}