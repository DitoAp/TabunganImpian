package com.example.tabunganimpian.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tabunganimpian.network.NetworkConfig
import com.example.tabunganimpian.response.ResultTabungan
import retrofit2.Call
import retrofit2.Response

class TabunganViewModel: ViewModel() {

    lateinit var recyclerListData: MutableLiveData<ResultTabungan>

    init {
        recyclerListData = MutableLiveData()
    }

    fun getTabunganItemObserverable() : MutableLiveData<ResultTabungan> {
        return recyclerListData
    }

    fun getTabunganItem() {
        NetworkConfig.getService().getDataTabungan()
            .enqueue(object: retrofit2.Callback<ResultTabungan> {
                override fun onResponse(
                    call: Call<ResultTabungan>,
                    response: Response<ResultTabungan>
                ) {
                    if (response.isSuccessful) {
                        recyclerListData.postValue(response.body())
                    } else {
                        recyclerListData.postValue(null)
                    }
                }

                override fun onFailure(call: Call<ResultTabungan>, t: Throwable) {
                    recyclerListData.postValue(null)
                }

            })
    }
}