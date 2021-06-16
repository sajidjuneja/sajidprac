package com.example.sajidprac.ui.exchangerates.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sajidprac.BR
import com.example.sajidprac.databinding.RowRatesBinding
import com.example.sajidprac.model.Currency

class ExchangeRatesAdapter :
    RecyclerView.Adapter<ExchangeRatesAdapter.MyViewHolder>() {
    var arrayList: ArrayList<Currency>
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RowRatesBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    init {
        arrayList= ArrayList()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val rates=arrayList[position]
        holder.binding.setVariable(BR.model,rates)
        holder.binding.executePendingBindings()
    }

    inner class MyViewHolder(var binding: RowRatesBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int {
        return arrayList.size
    }

    fun setMovieList(movies: ArrayList<Currency>) {
        this.arrayList = movies
        notifyDataSetChanged()
    }
}