package com.example.sajidprac.ui.exchangerates

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sajidprac.repository.RatesRepo
import com.example.sajidprac.model.RateResponse
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class ExchangeRatesViewModel: ViewModel() {

    val errorMessage = MutableLiveData<String>()
    val movieList = MutableLiveData<RateResponse>()
    val loading = MutableLiveData<Boolean>()
    val repo: RatesRepo = RatesRepo()
    var date = getCurrentDateTime()
    var dateInString = date.toString("yyyy-MM-dd")

    fun getAllMovies() {
      viewModelScope.launch {
            val response = repo.getRates(dateInString)
                if (response.isSuccessful) {
                    movieList.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
        }

    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }
}