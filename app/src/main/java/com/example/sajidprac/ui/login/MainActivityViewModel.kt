package com.example.sajidprac.ui.login

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel: ViewModel() {

    var username: String = ""
    var password: String = ""
    private val logInUsername = MutableLiveData<String>()
    private val logInPassword = MutableLiveData<String>()
    private val isLogin=MutableLiveData<Boolean>()

    fun getLogInUsername(): LiveData<String> = logInUsername
    fun getLogInPassword(): LiveData<String> = logInPassword
    fun isLogin(): LiveData<Boolean> = isLogin

    fun performValidation() {
        Log.v("user", logInUsername.value.toString())
        if (username.isBlank()) {
            logInUsername.value = "Enter username"
            return
        }

        logInUsername.value=null

        if (password.isBlank()) {
            logInPassword.value = "Invalid password"
            return
        }
        logInPassword.value=null

        isLogin.value = username == "test@android.com"&&password=="123456"
    }

}