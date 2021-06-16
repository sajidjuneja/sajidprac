package com.example.sajidprac.network

import com.example.sajidprac.model.RateResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("historical/{date}.json?app_id=b8170f2960a546378a5ceb06a7bb6f59")
    suspend fun getRates(@Path("date") dates:String): Response<RateResponse>
}