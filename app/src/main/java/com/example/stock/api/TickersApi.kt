package com.example.stock.api

import com.example.stock.model.Stock
import com.example.stock.model.TickersItem
import retrofit2.Response
import retrofit2.http.GET
//https://pkgstore.datahub.io/core/s-and-p-500-companies/constituents_json/data/0e5db1e7676fbd54248b1de218b5a908/constituents_json.json
interface TickersApi {
    @GET("core/s-and-p-500-companies/constituents_json/data/0e5db1e7676fbd54248b1de218b5a908/constituents_json.json")
    suspend fun getTickers(): List<TickersItem>
}