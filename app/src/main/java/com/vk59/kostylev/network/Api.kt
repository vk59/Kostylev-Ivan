package com.vk59.kostylev.network

import com.vk59.kostylev.model.ResponseData
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    @GET("latest/{page}?json=true")
    suspend fun getLatest(@Path("page") page: Int): ResponseData
}