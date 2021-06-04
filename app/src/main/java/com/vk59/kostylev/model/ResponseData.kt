package com.vk59.kostylev.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ResponseData: Serializable {
    @SerializedName("result")
    @Expose
    var result: List<ItemVideo>? = null

    @SerializedName("totalCount")
    @Expose
    var totalCount: Int? = null
}