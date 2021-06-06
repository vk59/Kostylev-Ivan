package com.vk59.kostylev.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vk59.kostylev.Event
import com.vk59.kostylev.model.ResponseData
import com.vk59.kostylev.network.Api
import com.vk59.kostylev.network.NetworkService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {
    var api: Api = NetworkService.retrofitService()

    fun requestWithLiveData(
        liveData: MutableLiveData<Event>,
        request: suspend () -> ResponseData
    ) {

        liveData.postValue(Event.loading())

        this.viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = request.invoke()
                if (response.result != null) {
                    liveData.postValue(Event.success(response.result))
                } else {
                    liveData.postValue(Event.error(null))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                liveData.postValue(Event.error(null))
            }
        }
    }
}