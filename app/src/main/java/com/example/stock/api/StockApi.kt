package com.example.stock.api

import com.example.stock.model.Stock

import retrofit2.Response
import retrofit2.http.GET
//https://raw.githubusercontent.com/sunya1/apiStock/master/stockInfo.json
interface StockApi {
    @GET("sunya1/apiStock/master/stockInfo.json")
    suspend fun getStockData(): Response<List<Stock>>
}