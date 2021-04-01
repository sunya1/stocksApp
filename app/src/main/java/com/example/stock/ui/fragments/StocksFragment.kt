package com.example.stock.ui.fragments

import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stock.App
import com.example.stock.GraphActivity
import com.example.stock.R
import com.example.stock.model.Stock
import com.example.stock.model.StockPrice
import com.example.stock.model.TickersItem

import com.example.stock.ui.adapter.OnFavouriteClickListener
import com.example.stock.ui.adapter.StocksFragmentAdapter
import com.example.stock.ui.viewmodels.MainViewModel
import com.example.stock.ui.viewmodels.MainViewModelFactory
import com.example.stock.ui.viewmodels.SharedViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_stocks.*
import kotlinx.coroutines.*
import okhttp3.internal.wait

const val NUM_OF_STOCKS: Int = 504
class StocksFragment : Fragment(R.layout.fragment_stocks) , OnFavouriteClickListener  {

    private lateinit var viewModel: MainViewModel


    private  var stocks = ArrayList<Stock>()
    private var prices = ArrayList<StockPrice>()
    private var fStock = ArrayList<Stock>()

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private val myAdapter by lazy { StocksFragmentAdapter(this) }


    private val args: StocksFragmentArgs by navArgs()
    private var tickers = ArrayList<TickersItem>()

    private var i = 1



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initVM()
        observeTickers()
        observeStockResponse()

        checkPricesInDb()


    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        checkForFilteredStock()
        checkForSuggestionClicked()
        setClickListenersTextView()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setClickListenersTextView() {
        favourite_text.setOnClickListener {
            myAdapter.setData(fStock)
            favourite_text.setTextAppearance(R.style.stocks)
            stocks_text.setTextAppearance(R.style.favourite)


        }
        stocks_text.setOnClickListener {
            myAdapter.setData(stocks)
            favourite_text.setTextAppearance(R.style.favourite)
            stocks_text.setTextAppearance(R.style.stocks)

        }
    }

    private fun checkForSuggestionClicked() {
        if(args.name != null) sharedViewModel.setSuggestion(args.name!!)
    }

    private fun checkForFilteredStock() {
        sharedViewModel.fStocks.observe(viewLifecycleOwner , {

            myAdapter.setData(it)
            Log.d("filtered in " , it.toString())
        })
    }

    private fun observeTickers(){
        viewModel.getTickers()

        viewModel.tickers.observe(this , {
            tickers = it as ArrayList<TickersItem>
            Thread.sleep(2000)
        })
    }

    private fun initVM() {
        val app = requireActivity().application as App
        val repo = app.repository
        val viewModelFactory = MainViewModelFactory(repo)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }
    private fun initRecyclerView() {

        recyclerView.adapter = myAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


    }

    private fun observeStockResponse() {
        viewModel.getStocks()
        viewModel.stockResponse.observe(this, Observer {
            sharedViewModel.setStocks(it.body() as List<Stock>)
            saveStocks(it.body())
            myAdapter.setData(it.body()!!)

        })



    }

    private fun checkPricesInDb(){
        viewModel.allPrices.observe(this, {
            Log.d("size of it", it.size.toString())
            if (it.size < NUM_OF_STOCKS) {
                observePriceResponse()
                prices = it as ArrayList<StockPrice>
            }
            else{
                prices = it as ArrayList<StockPrice>
            }

        })
    }

    private fun observePriceResponse() {




        CoroutineScope(Dispatchers.Main).launch{
            delay(1000)

                insertPrice(tickers[i].symbol, i ,tickers)
                i++

        }

    }
    fun insertPrice(ticker: String, i: Int, allTickers: ArrayList<TickersItem>) {
            var times = 0
            viewModel.getPrice(ticker)

            viewModel.priceResponse.observe(this, {

                if (it.isSuccessful && times == 0) {
                    times++
                    val price = it.body()!!
                    price.ticker = allTickers[i - 1].symbol


                    Log.d("price", "$ticker  $price $i")
                    viewModel.insert(price)

                    myAdapter.notifyDataSetChanged()

                } else Log.d("ERROR", it.message())
            })
    }
    fun saveStocks(list: List<Stock>?){
        stocks = list as ArrayList<Stock>
    }

    override fun addFavourite(stock: Stock) {
            fStock.add(stock)
            sharedViewModel.setFavourite(fStock)


    }
    override fun isFavourite(stock: Stock): Boolean {
        var found = false
        sharedViewModel.favouriteStock.observe(this , {

            if(!it.isNullOrEmpty() && it.contains(stock)) found = true
        })
        return found
    }

    override fun deleteItemFromFav(stock: Stock) {
        fStock.remove(stock)
        sharedViewModel.setFavourite(fStock)

    }

    override fun getPrice(ticker: String): StockPrice {
        var price = StockPrice()
        viewModel.allPrices.observe(this , {
            if(!it.isNullOrEmpty()){
                var price1 = it.find { stockPrice ->
                    stockPrice.ticker == ticker
                }
                if(price1 != null) price = price1

                Log.d("priceRequested" , price.toString())
            }
        })

        return  price
    }

    override fun showGraph(ticker: String , nameOfCompany:String) {
        var price = StockPrice()
        viewModel.allPrices.observe(this , { prices ->
            if(!prices.isNullOrEmpty() ){
                var check = prices.find { it.ticker == ticker }
                if(check != null) price = check
            }
        })
        val intent = Intent(context, GraphActivity::class.java).apply {
            putExtra("ticker", ticker)
            putExtra("name" , nameOfCompany)
            putExtra("price" , price)
        }
        startActivity(intent)
    }


}

