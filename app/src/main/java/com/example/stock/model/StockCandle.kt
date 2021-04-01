package com.example.stock.model

import com.google.gson.annotations.SerializedName

data class StockCandle(
        @SerializedName("c")
        val c : List<Double>
        )
