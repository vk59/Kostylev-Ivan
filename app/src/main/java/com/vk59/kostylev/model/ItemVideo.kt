package com.vk59.kostylev.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ItemVideo(
    @SerializedName("id")
    var id: Int?,

    @SerializedName("description")
    @Expose
    var description: String?,

    @SerializedName("votes")
    @Expose
    var votes: Int?,

    @SerializedName("author")
    @Expose
    var author: String?,

    @SerializedName("date")
    @Expose
    var date: String?,

    @SerializedName("gifURL")
    @Expose
    var gifURL: String?,

    @SerializedName("gifSize")
    @Expose
    var gifSize: Int?,

    @SerializedName("previewURL")
    @Expose
    var previewURL: String?,

    @SerializedName("videoURL")
    @Expose
    var videoURL: String?,

    @SerializedName("videoPath")
    @Expose
    var videoPath: String?,

    @SerializedName("videoSize")
    @Expose
    var videoSize: Int?,

    @SerializedName("type")
    @Expose
    var type: String?,

    @SerializedName("width")
    @Expose
    var width: String?,

    @SerializedName("height")
    @Expose
    var height: String?,

    @SerializedName("commentsCount")
    @Expose
    var commentsCount: Int?,

    @SerializedName("fileSize")
    @Expose
    var fileSize: Int?,

    @SerializedName("canVote")
    @Expose
    var canVote: Boolean?
)