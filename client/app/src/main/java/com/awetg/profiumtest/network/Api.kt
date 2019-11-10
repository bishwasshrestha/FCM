package com.awetg.profiumtest.network

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

object Api {

    const val URL = "http:// 10.69.50.41:3000"

    interface Service {

        @Multipart
        @POST("/api/upload")
        fun uploadImage(@Part image: MultipartBody.Part, @Part("my_media") name: RequestBody): Call<ResponseBody>

    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val service = retrofit.create(Service::class.java)

}