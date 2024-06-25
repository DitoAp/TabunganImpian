package com.example.tabunganimpian.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tabunganimpian.dto.TabunganItem
import com.example.tabunganimpian.models.TabunganModel
import com.example.tabunganimpian.network.NetworkConfig
import com.example.tabunganimpian.response.ResultItem
import com.example.tabunganimpian.response.ResultStatus
import com.example.tabunganimpian.response.ResultTabungan
import com.example.tabunganimpian.response.TabunganResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TabunganViewModel: ViewModel() {

    lateinit var recyclerListData: MutableLiveData<ResultTabungan>
    lateinit var recyclerEndListData: MutableLiveData<ResultTabungan>
    lateinit var getDetailLiveData: MutableLiveData<TabunganResponse>
    lateinit var createTabunganLiveData: MutableLiveData<ResultStatus>
    lateinit var deleteLiveData: MutableLiveData<ResultStatus>
    lateinit var itemsLiveData: MutableLiveData<ResultItem>
    lateinit var createSavingLiveData: MutableLiveData<ResultStatus>

    init {
        createTabunganLiveData = MutableLiveData()
        recyclerListData = MutableLiveData()
        recyclerEndListData = MutableLiveData()
        getDetailLiveData = MutableLiveData()
        deleteLiveData = MutableLiveData()
        itemsLiveData = MutableLiveData()
        createSavingLiveData = MutableLiveData()
    }
    fun getTabunganItemObserverable() : MutableLiveData<ResultTabungan> {
        return recyclerListData
    }

    fun getTabunganEndObserverable() : MutableLiveData<ResultTabungan> {
        return recyclerEndListData
    }

    fun getCreateTabunganObserverable() : MutableLiveData<ResultStatus> {
        return createTabunganLiveData
    }

    fun getDetailTabunganObserverable() : MutableLiveData<TabunganResponse> {
        return getDetailLiveData
    }

    fun deleteTabunganObserverable() : MutableLiveData<ResultStatus> {
        return deleteLiveData
    }

    fun getItemsObserverable(): MutableLiveData<ResultItem> {
        return itemsLiveData
    }

    fun getCreateSavingObserverable() : MutableLiveData<ResultStatus> {
        return createSavingLiveData
    }

    fun getEndData() {
        NetworkConfig.getService().endSavings()
            .enqueue(object: retrofit2.Callback<ResultTabungan> {
                override fun onResponse(
                    call: Call<ResultTabungan>,
                    response: Response<ResultTabungan>
                ) {
                    if (response.isSuccessful) {
                        recyclerEndListData.postValue(response.body())
                    } else {
                        recyclerEndListData.postValue(null)
                    }
                }

                override fun onFailure(call: Call<ResultTabungan>, t: Throwable) {
                    recyclerEndListData.postValue(null)
                }

            })
    }

    fun createItem(tabungan_id: String, currency: Double, category: String) {
        NetworkConfig.getService().savingItems(tabungan_id, currency, category)
            .enqueue(object: retrofit2.Callback<ResultStatus> {
                override fun onResponse(
                    call: Call<ResultStatus>,
                    response: Response<ResultStatus>
                ) {
                    if (response.isSuccessful) {
                        createSavingLiveData.postValue(response.body())
                    } else {
                        createSavingLiveData.postValue(null)
                    }
                }

                override fun onFailure(call: Call<ResultStatus>, t: Throwable) {
                    createSavingLiveData.postValue(null)
                }

            })
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

    fun getDetailTabungan(id: String) {
        NetworkConfig.getService().getDetailTabungan(id)
            .enqueue(object : Callback<TabunganResponse> {
                override fun onResponse(
                    call: Call<TabunganResponse>,
                    response: Response<TabunganResponse>
                ) {
                    if (response.isSuccessful) {
                        getDetailLiveData.postValue(response.body())
                    } else {
                        getDetailLiveData.postValue(null)
                    }
                }

                override fun onFailure(call: Call<TabunganResponse>, t: Throwable) {
                    getDetailLiveData.postValue(null)
                }

            })
    }

    fun createTabunganItem(name: RequestBody, tipe: RequestBody, target: RequestBody, total: RequestBody, savings: RequestBody , image: MultipartBody.Part, _method: RequestBody) {
        NetworkConfig.getService().addDataTabungan(name, tipe, target, total, savings, image, _method)
            .enqueue(object: Callback<ResultStatus>{
                override fun onResponse(
                    call: Call<ResultStatus>,
                    response: Response<ResultStatus>
                ) {
                    if (response.isSuccessful) {
                        createTabunganLiveData.postValue(response.body())
                    } else {
                        createTabunganLiveData.postValue(null)
                    }
                }

                override fun onFailure(call: Call<ResultStatus>, t: Throwable) {
                    createTabunganLiveData.postValue(null)
                }

            })

    }

    fun updateTabunganItem(id: String, name: String, tipe: String, target: String, total: Double, savings: String ) {
        NetworkConfig.getService().updateTabungan(id, name, tipe, target, total, savings)
            .enqueue(object : Callback<ResultStatus> {
                override fun onResponse(
                    call: Call<ResultStatus>,
                    response: Response<ResultStatus>
                ) {
                    if (response.isSuccessful) {
                       createTabunganLiveData.postValue(response.body())
                    } else {
                        createTabunganLiveData.postValue(null)
                    }
                }

                override fun onFailure(call: Call<ResultStatus>, t: Throwable) {
                    createTabunganLiveData.postValue(null)
                }

            })
    }

    fun deleteTabunganItem(id: String) {
        NetworkConfig.getService().deleteTabungan(id)
            .enqueue(object : Callback<ResultStatus> {
                override fun onResponse(
                    call: Call<ResultStatus>,
                    response: Response<ResultStatus>
                ) {
                    if (response.isSuccessful) {
                        deleteLiveData.postValue(response.body())
                    } else {
                        deleteLiveData.postValue(null)
                    }
                }

                override fun onFailure(call: Call<ResultStatus>, t: Throwable) {
                    deleteLiveData.postValue(null)
                }

            })
    }
}