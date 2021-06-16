package com.example.sajidprac.utils

import android.content.Context
import android.widget.Toast

class Utils {

    fun Context.myToast(message: String)
    {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}