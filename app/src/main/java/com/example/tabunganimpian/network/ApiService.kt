package com.example.tabunganimpian.network

import com.example.tabunganimpian.response.ResultItem
import com.example.tabunganimpian.response.ResultStatus
import com.example.tabunganimpian.response.ResultTabungan
import com.example.tabunganimpian.response.TabunganResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @GET("tabungan")
    fun getDataTabungan(): Call<ResultTabungan>

    @Multipart
    @POST("tabungan")
    fun addDataTabungan(@Part("name") name: RequestBody,
                        @Part("tipe") tipe: RequestBody,
                        @Part("target") target: RequestBody,
                        @Part("total") total: RequestBody,
                        @Part("menabung") savings: RequestBody,
                        @Part image: MultipartBody.Part,
                        @Part("_method") _method: RequestBody
                        ) : Call<ResultStatus>

    @GET("detail/{tabungan_id}")
    fun getDetailTabungan(@Path("tabungan_id") tabungan_id: String) : Call<TabunganResponse>

    @FormUrlEncoded
    @PUT("tabungan/{tabungan_id}")
    fun updateTabungan(@Path("tabungan_id") tabungan_id: String,
                       @Field("name") name: String,
                       @Field("tipe") tipe: String,
                       @Field("target") target: String,
                       @Field("total") total: Double,
                       @Field("menabung") savings: String
    ) : Call<ResultStatus>

    @GET("tabungan/tercapai")
    fun endSavings() : Call<ResultTabungan>

    @GET("tabungan/{tabungan_id}")
    fun deleteTabungan(@Path("tabungan_id") tabungan_id: String) : Call<ResultStatus>

    @GET("items/{items_id}")
    fun getItems(@Path("items_id") items_id: String) : Call<ResultItem>

    @FormUrlEncoded
    @POST("items/increment")
    fun savingItems(@Field("tabungan") tabungan_id: String,
                    @Field("currency") currency: Double,
                    @Field("category") category: String
                    ) : Call<ResultStatus>
}