package com.example.stock.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.stock.api.RetrofitInstance
import com.example.stock.db.PriceDao
import com.example.stock.model.Stock
import com.example.stock.model.StockCandle
import com.example.stock.model.StockPrice
import com.example.stock.model.TickersItem
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Response
import java.io.IOException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Repository(private val priceDao: PriceDao) {
    val allPrices: LiveData<List<StockPrice>> = priceDao.getPrices()

    fun insert(price: StockPrice) {
        runOnBackground {

            it.submit { priceDao.insert(price) }
        }

    }

    fun deleteAll(){
        runOnBackground {
            it.submit{ priceDao.deleteAll() }
        }
    }

    suspend fun getPrice(ticker: String): Response<StockPrice> {
        return RetrofitInstance.api.getPrice(ticker)
    }
    suspend fun getStockData(): Response<List<Stock>> {
        return RetrofitInstance.myApi.getStockData()
    }
    suspend fun getTickers(): List<TickersItem> {
        return RetrofitInstance.tickersApi.getTickers()
    }
    suspend fun getRes(ticker: String, resolution: String, from: Long): Response<StockCandle>? {
        return RetrofitInstance.api.getCandles(ticker , resolution ,from, System.currentTimeMillis() / 1000  )
    }
}

    private fun runOnBackground(submit: (ExecutorService) -> Unit) {
        val executor = Executors.newCachedThreadPool()
        try {
            submit.invoke(executor)
        } catch (e: IOException) {
            e.printStackTrace() // don't eat exceptions in prod
        } finally {
            executor.shutdown()
        }
    }

