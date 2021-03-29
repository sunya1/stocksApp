package com.example.stock.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.stock.model.StockPrice

@Database(entities = [StockPrice::class], version = 1)
abstract class PriceRoomDatabase : RoomDatabase() {

    abstract fun priceDao(): PriceDao

    companion object {
        @Volatile
        private var INSTANCE: PriceRoomDatabase? = null

        fun getDatabase(context: Context): PriceRoomDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PriceRoomDatabase::class.java,
                    "price_database"
                ).fallbackToDestructiveMigration().build()

                INSTANCE = instance
                instance
            }
        }
    }
}