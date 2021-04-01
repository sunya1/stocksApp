package com.example.stock.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stock.api.RetrofitInstance
import com.example.stock.model.StockCandle
import com.example.stock.model.StockPrice
import com.example.stock.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class GraphViewModel(): ViewModel() {
//    private val _sCandle = MutableLiveData<StockCandle>()
//    var sCandle: LiveData<StockCandle> = _sCandle
val graphResponse: MutableLiveData<Response<StockCandle>> = MutableLiveData()
    val graph: LiveData<Response<StockCandle>> = graphResponse


    fun getCandles(ticker: String , resolution: String , from : Long ){
        viewModelScope.launch {

                val response = RetrofitInstance.api.getCandles(ticker , resolution ,from , System.currentTimeMillis() / 1000)
                Log.d("response" , "$ticker $resolution$from")
                graphResponse.value = response



        }
    }

}