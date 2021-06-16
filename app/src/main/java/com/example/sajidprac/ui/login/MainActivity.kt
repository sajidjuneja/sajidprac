package com.example.sajidprac.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.sajidprac.model.Currency
import com.example.sajidprac.ui.exchangerates.ExchangeRatesActivity
import com.example.sajidprac.R
import com.example.sajidprac.model.RateResponse
import com.example.sajidprac.databinding.ActivityMainBinding
import com.example.sajidprac.network.ApiClient
import com.example.sajidprac.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding
    lateinit var viewModel: MainActivityViewModel
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding=DataBindingUtil.setContentView(this, R.layout.activity_main)
        sessionManager= SessionManager(this)
        sessionManager.checkLogin()

        activityMainBinding.lifecycleOwner = this

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        activityMainBinding.viewmodel=viewModel

        viewModel.getLogInUsername().observe(this, { result ->
            if (result!=null){
                activityMainBinding.loginPhoneNumber.error = result
            }
            else {
                activityMainBinding.loginPhoneNumber.isErrorEnabled = false
            }
        })

        viewModel.getLogInPassword().observe(this, { result ->
            if (result!=null){
                activityMainBinding.loginPassword.error = result
            }
            else {
                activityMainBinding.loginPassword.isErrorEnabled = false
            }
        })

        viewModel.isLogin().observe(this,{
            if (it){
                Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
                sessionManager.createSession("login")
                startActivity(Intent(this, ExchangeRatesActivity::class.java))
                finish()
            }
            else{
                Toast.makeText(this, "Please enter valid username and password.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}