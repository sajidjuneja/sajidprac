package com.example.sajidprac.repository

import com.example.sajidprac.network.ApiClient

class RatesRepo {

    private val apiInterface=ApiClient.apiService

    suspend fun getRates(dates:String)=apiInterface.getRates(dates)
}