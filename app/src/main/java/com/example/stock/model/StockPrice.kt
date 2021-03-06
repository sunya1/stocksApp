package com.example.stock.model


import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "price_table")
data class StockPrice (

        @PrimaryKey @ColumnInfo(name = "ticker") var ticker: String = " ",
        @ColumnInfo(name = "curr_price") var c: Double = 0.0,
        @ColumnInfo(name = "highest_price") var h: Double = 0.0,
        @ColumnInfo(name = "lowest_price") var l: Double = 0.0,
        @ColumnInfo(name = "open_price") var o: Double = 0.0,
        @ColumnInfo(name = "prevClose_price") var pc: Double = 0.0,
        @ColumnInfo(name = "t_price") var t: Int = 0
): Parcelable
//@ColumnInfo(name = "ticker") var ticker: String = " ",

