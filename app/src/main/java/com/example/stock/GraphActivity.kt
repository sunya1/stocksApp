package com.example.stock

import android.R.*
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.stock.model.StockCandle
import com.example.stock.model.StockPrice
import com.example.stock.ui.viewmodels.GraphViewModel
import com.example.stock.ui.viewmodels.MainViewModelFactory
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.fragment_graph.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.properties.Delegates


class GraphActivity: AppCompatActivity(R.layout.fragment_graph){
    private lateinit var graphVm: GraphViewModel
    private var favPressed = false
    private lateinit var ticker: String
    private lateinit var resolution: String
    private var from  = System.currentTimeMillis() / 1000 - (7 * 24 * 60 * 60)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initVM()

        bindUI()
        setclickListeners()
    }



    private fun initVM() {
        graphVm = ViewModelProvider(this).get(GraphViewModel::class.java)
    }

    @SuppressLint("SetTextI18n")
    private fun bindUI() {
        var name = intent.getStringExtra("name").toString()
        ticker = intent.getStringExtra("ticker").toString()
        var price = intent.getParcelableExtra<StockPrice>("price")

        ticker_of_company.text = ticker
        name_of_company.text = name
        stock_price.text = price?.c.toString() + "$"
        graphVm.getCandles(ticker , "5" , from)

        buy_button.text = "But For " +  price?.c.toString()  + "$"
        var delta = price!!.c - price.pc
        if(delta < 0 ){

            change_of_price.setTextColor(Color.parseColor("#B22424"))
            change_of_price.text = "-$" + String.format("%.2f", (delta * -1)) + "(" + String.format("%.2f", (delta * -1) / price.c)  + "%)"
        }
        else{
            if(price.c == 0.0){
                change_of_price.text = "+$0.0(0.0%)"
            }
            else{
                change_of_price.setTextColor(Color.parseColor("#24B25D"))
                change_of_price.text = "+$" +String.format("%.2f", (delta))  + "(" +  String.format("%.2f", (delta) / price.c)  + "%)"
            }


        }
        graphVm.graphResponse.observe(this , {
            drawGraph(it.body()!!)
        })

        var suggestions = arrayListOf<TextView>(one_day_period , one_week_period , one_month_period , half_year_period , one_year_period , for_all_period)
        for(button in suggestions) setClickListener(button)

    }

    private fun setClickListener(button: TextView) {
        button.setOnClickListener {
            findFrom(button.text.toString())
            graphVm.getCandles(ticker , resolution , from)
            graphVm.graph.observe(this, {
                if(it.isSuccessful){
                    drawGraph(it.body()!!)

            }
            else{
                Log.d("ERROR" , it.errorBody().toString())
            }

        })
        }
    }
    private fun drawGraph(candle: StockCandle){
        val entries = arrayListOf<Entry>()
        val closePriceList = candle.c
        var x = 0
        for (closePrice in closePriceList) {
            entries.add(Entry(x.toFloat(), closePrice.toFloat()))
            x += 10
        }
        val dataSet = LineDataSet(entries, ticker)

        dataSet.setDrawCircles(false)
        dataSet.setDrawValues(false)
        dataSet.color = Color.BLACK
        dataSet.lineWidth = 1f

        dataSet.setDrawFilled(true)

        val drawable = ContextCompat.getDrawable(this@GraphActivity, R.drawable.gradient)

        dataSet.fillDrawable = drawable

        val lineData = LineData(dataSet)
        line_chart.data = lineData
        line_chart.invalidate()

        line_chart.minimumWidth = 1000
        line_chart.legend.isEnabled = false
        line_chart.setNoDataText("")
        line_chart.description.isEnabled = false
        line_chart.xAxis.isEnabled = false
        line_chart.axisLeft.isEnabled = false
        line_chart.axisRight.isEnabled = false
    }
    private fun findFrom(key : String) {
        var to = System.currentTimeMillis() / 1000
        when(key){
            "D" -> { from = to  - (7 * 24 * 60 * 60)
                    resolution = "5"}
            "W" -> { from = to  - (30 *  24 * 60 * 60)
                    resolution = "D"}
            "M" -> { from = to  - (30 * 24 * 60 * 60)
                    resolution = "30"}
            "6M" -> { from = to  - (182 * 24 * 60 * 60)
                    resolution = "D"}
            "1Y" -> { from = to  - (365 * 24 * 60 * 60)
                    resolution = "D"}
            "All" -> { from = 0L
                    resolution = "M"}
        }
    }

    private fun setclickListeners() {
        back_button.setOnClickListener{
            super.onBackPressed()
        }
        add_fav.setOnClickListener {
            if(!favPressed){
                add_fav.setImageResource(R.drawable.ic_favourite)
                favPressed = true
            }
            else {
                add_fav.setImageResource(R.drawable.ic_add_favourite2)
                favPressed = false
            }
        }
    }


}