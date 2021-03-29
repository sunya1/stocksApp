package com.example.stock.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stock.model.Stock
import com.example.stock.model.StockPrice
import com.example.stock.model.TickersItem
import com.example.stock.repository.Repository
import kotlinx.coroutines.*
import okhttp3.internal.wait
import retrofit2.Response

class MainViewModel(private val repository: Repository): ViewModel() {

    val priceResponse: MutableLiveData<Response<StockPrice>> = MutableLiveData()
    val stockResponse: MutableLiveData<Response<List<Stock>>> = MutableLiveData()
    val tickers: MutableLiveData<List<TickersItem>> = MutableLiveData()
    val allPrices: LiveData<List<StockPrice>> = repository.allPrices

    fun insert(stockPrice: StockPrice) {

        repository.insert(stockPrice)
    }
    fun deleteAll(){
        repository.deleteAll()
    }

    fun getPrice(ticker: String) {
        viewModelScope.launch {
            val response = repository.getPrice(ticker)
            priceResponse.value = response

        }


    }
    fun getStocks(){
        viewModelScope.launch {
            val response = repository.getStockData()

            stockResponse.value = response
        }
    }
    fun getTickers(){
        viewModelScope.launch {
            val response = repository.getTickers()

            tickers.value = response
        }
    }

}