package com.example.stock.model


import com.google.gson.annotations.SerializedName

data class TickersItem(

    @SerializedName("Symbol")
    val symbol: String
)