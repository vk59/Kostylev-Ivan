package com.vk59.kostylev.ui.main

import androidx.lifecycle.MutableLiveData
import com.vk59.kostylev.Event
import com.vk59.kostylev.ui.BaseViewModel

class MainViewModel : BaseViewModel() {
    val simpleLiveData = MutableLiveData<Event>()

    fun getLatest(page: Int) {
        requestWithLiveData(simpleLiveData) {
            api.getLatest(
                page = page
            )
        }
    }
}