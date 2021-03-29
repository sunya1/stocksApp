package com.example.stock.model


data class Stock(

//        val ticker: String?,
//        val name: String?,
//        val shareOutstanding: Double?,
//        val logo: String?,
            val name: String ,
            val ticker: String ,
            val logo: String

){
    override fun equals(other: Any?): Boolean {
        if(javaClass != other?.javaClass){
            return false
        }
        other as Stock

        if(ticker != other.ticker) return false
        if(name != other.name) return false
        if(logo != other.logo) return false

        return true
    }
}
//val currentPrice: Double? ,
//        val changeInADay: Double? ,