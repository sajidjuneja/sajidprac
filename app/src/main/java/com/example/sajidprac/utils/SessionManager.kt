package com.example.sajidprac.utils

import android.content.Context
import android.content.Intent

import android.content.SharedPreferences
import com.example.sajidprac.ui.exchangerates.ExchangeRatesActivity
import com.example.sajidprac.ui.login.MainActivity


class SessionManager(context: Context) {
    var sharedPreferences: SharedPreferences
    var editor: SharedPreferences.Editor
    var context: Context
    var PRIVATE_MODE = 0
    fun createSession(name: String?) {
        editor.putBoolean(LOGIN, true)
        editor.putString(NAME, name)
        editor.apply()
    }
    val isLoggin: Boolean
        get() = sharedPreferences.getBoolean(LOGIN, false)

    fun checkLogin() {
        if (isLoggin) {
            val i = Intent(context, ExchangeRatesActivity::class.java)
            context.startActivity(i)
            (context as MainActivity).finish()
        }
    }

    val userDetail: HashMap<String, String?>
        get() {
            val user: HashMap<String, String?> = HashMap()
            user[NAME] =
                sharedPreferences.getString(NAME, null)
            return user
        }

    fun logout() {
        editor.clear()
        editor.commit()
        val i = Intent(context, MainActivity::class.java)
        context.startActivity(i)
        (context as ExchangeRatesActivity).finish()
    }

    companion object {
        private const val PREF_NAME = "LOGIN"
        private const val LOGIN = "IS_LOGIN"
        const val NAME = "NAME"
    }

    init {
        this.context = context
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = sharedPreferences.edit()
    }
}