package com.example.catfacts.request

import androidx.lifecycle.MutableLiveData
import com.example.catfacts.network.ListDataUiState
import com.example.catfacts.network.apiService
import com.example.common_plugin.base.viewmodel.BaseViewModel
import com.example.common_plugin.ext.requestNoCheck
import com.example.data_plugin.model.FactDetailResponse
import com.example.data_plugin.model.FactResponse

class RequestCatFactsViewModel : BaseViewModel() {

    private var pageNo = 1

    var factListDataState = MutableLiveData<ListDataUiState<FactResponse>>()

    var factDetailDataState = MutableLiveData<FactDetailResponse?>()

    fun getCatFactsData(isRefresh: Boolean) {
        if (isRefresh) {
            pageNo = 1
        }
        requestNoCheck({ apiService.getCatFacts(20) }, {
            //请求成功
            pageNo++
            val listDataUiState =
                ListDataUiState(
                    isSuccess = true,
                    isRefresh = isRefresh,
                    isEmpty = it.isEmpty(),
                    hasMore = true,
                    isFirstEmpty = isRefresh && it.isEmpty(),
                    listData = it
                )
            factListDataState.value = listDataUiState
        }, {
            //请求失败
            val listDataUiState =
                ListDataUiState(
                    isSuccess = false,
                    errMessage = it.errorMsg,
                    isRefresh = isRefresh,
                    listData = arrayListOf<FactResponse>()
                )
            factListDataState.value = listDataUiState
        })
    }

    fun getCatFactDetailData(factId: String) {
        requestNoCheck({ apiService.getCatFactDetail(factId) }, {
            factDetailDataState.value = it
        }, {
            factDetailDataState.value = null
        })
    }
}