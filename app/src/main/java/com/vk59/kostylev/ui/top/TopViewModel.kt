package com.vk59.kostylev.ui.top

import androidx.lifecycle.MutableLiveData
import com.vk59.kostylev.Event
import com.vk59.kostylev.ui.BaseViewModel

class TopViewModel : BaseViewModel() {
    val simpleLiveData = MutableLiveData<Event>()

    fun getTop(page: Int) {
        requestWithLiveData(simpleLiveData) {
            api.getTop(
                page = page
            )
        }
    }
}