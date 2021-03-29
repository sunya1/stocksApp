package com.example.stock.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stock.model.Stock

class SharedViewModel: ViewModel() {
    private var _stocks = MutableLiveData<List<Stock>>()
    var stocks : LiveData<List<Stock>> = _stocks

    private var _fStocks = MutableLiveData<List<Stock>>()
    var fStocks : LiveData<List<Stock>> = _fStocks

    private var _suggestion = MutableLiveData<String>()
    var suggestion: LiveData<String> = _suggestion

    private var _favouriteStock = MutableLiveData<List<Stock>>()
    var favouriteStock: LiveData<List<Stock>> = _favouriteStock

    fun setStocks(stock: List<Stock>){
        _stocks.value = stock
    }
    fun setFStock(stock: List<Stock>){
        _fStocks.value = stock
    }
    fun setSuggestion(name: String){
        _suggestion.value = name
    }
    fun setFavourite(stocks: List<Stock>){
        _favouriteStock.value = stocks
    }

}