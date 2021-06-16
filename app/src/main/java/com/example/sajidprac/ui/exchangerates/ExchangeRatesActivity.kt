package com.example.sajidprac.ui.exchangerates

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.example.sajidprac.R
import com.example.sajidprac.databinding.ActivityExchangeRatesBinding
import com.example.sajidprac.model.Currency
import com.example.sajidprac.ui.exchangerates.adapter.ExchangeRatesAdapter
import com.example.sajidprac.utils.SessionManager
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ExchangeRatesActivity : AppCompatActivity() {

    private var mDay: Int=0
    private var mMonth: Int=0
    private var mYear: Int=0
    lateinit var activityExchangeRatesBinding: ActivityExchangeRatesBinding
    lateinit var viewModel: ExchangeRatesViewModel
    lateinit var arrayList: ArrayList<Currency>
    var adapter: ExchangeRatesAdapter = ExchangeRatesAdapter()
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityExchangeRatesBinding=DataBindingUtil.setContentView(this,R.layout.activity_exchange_rates)
        sessionManager= SessionManager(this)

        activityExchangeRatesBinding.lifecycleOwner = this

        viewModel = ViewModelProvider(this).get(ExchangeRatesViewModel::class.java)
        val c1 = Calendar.getInstance().time
        val df = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formattedDate: String = df.format(c1)
        activityExchangeRatesBinding.edittext.text = formattedDate
        viewModel.movieList.observe(this,{ response ->
            val ratess:HashMap<String, BigDecimal> = response.rates!!
            val rates1=ratess.toSortedMap(compareBy{it})
            var currency:Currency
            arrayList=ArrayList()
            rates1.forEach { (key, value) ->
                run {
                    currency = Currency(key, value)
                    arrayList.add(currency)
                }
            }
            activityExchangeRatesBinding.recyclerView.hasFixedSize()
            activityExchangeRatesBinding.recyclerView.layoutManager= GridLayoutManager(this,3)
            activityExchangeRatesBinding.recyclerView.adapter=adapter
            activityExchangeRatesBinding.recyclerView.itemAnimator=DefaultItemAnimator()
            adapter.setMovieList(arrayList)

        })
        viewModel.loading.observe(this,{
            if (!it){
                activityExchangeRatesBinding.progressBar.visibility=View.GONE
            }
        })
        activityExchangeRatesBinding.imgLogout.setOnClickListener {
            sessionManager.logout()
        }
        activityExchangeRatesBinding.progressBar.visibility=View.VISIBLE
        viewModel.getAllMovies()
        activityExchangeRatesBinding.btnSearch.setOnClickListener {
            activityExchangeRatesBinding.progressBar.visibility=View.VISIBLE
            viewModel.getAllMovies()
        }
        activityExchangeRatesBinding.edittext.setOnClickListener {
            setDate()
        }
    }

    fun setDate(){
        val c: Calendar = Calendar.getInstance()
        mYear = c.get(Calendar.YEAR)
        mMonth = c.get(Calendar.MONTH)
        mDay = c.get(Calendar.DAY_OF_MONTH)
        var mt: String
        var dy: String //local variable
        val datePickerDialog = DatePickerDialog(this,
            { view, year, monthOfYear, dayOfMonth ->

                mt =
                    if (monthOfYear+1 < 10) "0${monthOfYear+1}"
                    else java.lang.String.valueOf(monthOfYear+1)
                Log.v("mt",mt)

                dy = if (dayOfMonth < 10) "0$dayOfMonth" else java.lang.String.valueOf(dayOfMonth)

                activityExchangeRatesBinding.edittext.text = java.lang.String.format(
                    Locale.getDefault(),
                    "%d-%s-%s",
                    year,
                    mt,
                    dy
                )
                viewModel.dateInString=java.lang.String.format(
                    Locale.getDefault(),
                    "%d-%s-%s",
                    year,
                   mt,
                    dy
                )
            }, mYear, mMonth, mDay
        )
        datePickerDialog.datePicker.maxDate = Calendar.getInstance().timeInMillis;
        datePickerDialog.show()
    }
}