package com.vk59.kostylev.ui.hot

import androidx.lifecycle.MutableLiveData
import com.vk59.kostylev.Event
import com.vk59.kostylev.ui.BaseViewModel

class HotViewModel : BaseViewModel() {
    val simpleLiveData = MutableLiveData<Event>()

    fun getHot(page: Int) {
        requestWithLiveData(simpleLiveData) {
            api.getHot(
                page = page
            )
        }
    }
}