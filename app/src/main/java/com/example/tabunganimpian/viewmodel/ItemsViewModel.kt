package com.example.tabunganimpian.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tabunganimpian.network.NetworkConfig
import com.example.tabunganimpian.response.ResultItem
import retrofit2.Call
import retrofit2.Response

class ItemsViewModel: ViewModel() {

    lateinit var itemsLiveData: MutableLiveData<ResultItem>

    init {
        itemsLiveData = MutableLiveData()
    }

    fun getItemsObserverable(): MutableLiveData<ResultItem> {
        return itemsLiveData
    }

    fun getItems(id: String) {
        NetworkConfig.getService().getItems(id)
            .enqueue(object: retrofit2.Callback<ResultItem> {
                override fun onResponse(call: Call<ResultItem>, response: Response<ResultItem>) {
                    if (response.isSuccessful) {
                        itemsLiveData.postValue(response.body())
                    } else {
                        itemsLiveData.postValue(null)
                    }
                }

                override fun onFailure(call: Call<ResultItem>, t: Throwable) {
                    itemsLiveData.postValue(null)
                }

            })
    }
}