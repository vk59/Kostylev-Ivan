package com.vk59.kostylev

import com.vk59.kostylev.model.ItemVideo

data class Event(val status: Status, val data: List<ItemVideo>?, val error: Error?) {

    companion object {
        fun loading(): Event{
            return Event(Status.LOADING, null, null)
        }

        fun success(data: List<ItemVideo>?): Event {
            return Event(Status.SUCCESS, data, null)
        }

        fun error(error: Error?): Event {
            return Event(Status.ERROR, null, error)
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}