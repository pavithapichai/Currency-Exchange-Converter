package com.example.paypaycurrencyconverter.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.paypaycurrencyconverter.databinding.ItemCurrencyExchangeBinding
import com.example.paypaycurrencyconverter.local.entity.CurrencyEntity
import java.text.DecimalFormat

class CurrencyConvertAdapter : RecyclerView.Adapter<CurrencyConvertAdapter.CurrencyViewHolder>() {

    private val currencies = arrayListOf<CurrencyEntity>()
    private lateinit var binding: ItemCurrencyExchangeBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemCurrencyExchangeBinding.inflate(inflater,parent,false)
        return CurrencyViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bindItem(currencies[position]);
        holder.setIsRecyclable( false)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    inner class CurrencyViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        fun bindItem(currencyEntity: CurrencyEntity) {
            binding.currencyName.text = currencyEntity.id
            binding.currencyRate.text = DecimalFormat("#.###").format(currencyEntity.baseRate)
        }
    }
}