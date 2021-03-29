package com.example.stock.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.stock.model.StockPrice


@Dao
interface PriceDao {

    @Query("SELECT * FROM price_table ORDER BY ticker ASC")
    fun getPrices(): LiveData<List<StockPrice>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(price: StockPrice)

    @Query("DELETE FROM price_table")
    fun deleteAll()

    @Query("UPDATE price_table SET curr_price = :newPrice WHERE curr_price = :lastPrice")
    fun update(newPrice: Double, lastPrice: Double)

    @Delete
    fun delete(stockPrice: StockPrice)

    @Query("SELECT EXISTS (SELECT 1 FROM price_table WHERE ticker = :curr)")
    fun exists(curr: String): Boolean

    @Query("SELECT * FROM price_table WHERE ticker = :tic")
    fun getPriceDB(tic: String): LiveData<StockPrice>

}