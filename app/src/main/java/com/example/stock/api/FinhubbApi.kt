package com.example.stock.api

import com.example.stock.model.Stock
import com.example.stock.model.StockPrice
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query



//https://finnhub.io/api/v1/stock/profile2?symbol=AAPL&token=c0n40nn48v6v1q0bvs20 profile
//https://finnhub.io/api/v1/quote?symbol=AAPL&token=c0n40nn48v6v1q0bvs20 price

interface FinhubbApi {


    @GET("stock/profile2?")
    suspend fun getStock(@Query("symbol") ticker: String): Response<Stock>
    @GET("quote?")
    suspend fun getPrice(@Query("symbol") ticker: String): Response<StockPrice>


}