package com.example.stock

import android.app.Application
import com.example.stock.db.PriceRoomDatabase
import com.example.stock.repository.Repository

class App : Application() {
    private val database by lazy { PriceRoomDatabase.getDatabase(this) }
    val repository by lazy { Repository(database.priceDao()) }

}