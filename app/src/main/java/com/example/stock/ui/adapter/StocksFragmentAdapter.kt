package com.example.stock.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.stock.R
import com.example.stock.model.Stock
import com.example.stock.model.StockPrice
import com.squareup.picasso.Picasso
import kotlin.collections.ArrayList


class StocksFragmentAdapter(private val itemClickListener: OnFavouriteClickListener) : RecyclerView.Adapter<StocksFragmentAdapter.StocksViewHolder>() {
    private var stocks: ArrayList<Stock> = ArrayList()
    private var prices: ArrayList<StockPrice> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StocksViewHolder {

        return StocksViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.stock_item, parent, false))

    }

    override fun onBindViewHolder(holder: StocksViewHolder, position: Int) {
            holder.itemView.setBackgroundResource(R.drawable.layout_bg)
            if(position % 2 != 0){
                holder.itemView.setBackgroundColor(Color.parseColor("#F0F4F7"))
                holder.itemView.setBackgroundResource(R.drawable.layout_bg)

            }
        else{
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"))

            }

        if(itemClickListener.isFavourite(stocks[position])){
            holder.itemView.findViewById<ImageView>(R.id.add_favourite).setImageResource(R.drawable.ic_favourite)
        }

        holder.bind(stocks[position] , itemClickListener )






    }

    override fun getItemCount(): Int {
        return stocks.size
    }

    fun setData(newList: List<Stock>) {

        Log.d("filtere" , newList.toString())
        stocks = newList as ArrayList<Stock>

        notifyDataSetChanged()


    }



    open class StocksViewHolder(view: View): RecyclerView.ViewHolder(view){

        private val nameOfCompany : TextView = view.findViewById(R.id.name_of_company_text)
        private val nameOfStock : TextView = view.findViewById(R.id.name_of_stock_text)
        private val costOfStock : TextView = view.findViewById(R.id.cost_of_stock_text)
        private val logoOfCompany: ImageView = view.findViewById(R.id.company_logo)
        private val changeOfCost: TextView = view.findViewById(R.id.change_of_cost_text)
        private val icon: ImageView = view.findViewById(R.id.add_favourite)

        @SuppressLint("SetTextI18n")
        fun bind(stock: Stock, itemClickListener: OnFavouriteClickListener){
            Log.d("binding", stock.toString())
            if(stock.name.length > 20){
                nameOfCompany.text = stock.name.substring(0 , 20) + "..."
            }

            else{
                nameOfCompany.text = stock.name
            }

            nameOfStock.text = stock.ticker

            var price = itemClickListener.getPrice(stock.ticker)

            if(!itemClickListener.isFavourite(stock)){
                icon.setImageResource(R.drawable.ic_add_favourite)
            }
            icon.setOnClickListener {
                if(itemClickListener.isFavourite(stock) ){
                    icon.setImageResource(R.drawable.ic_add_favourite)
                    itemClickListener.deleteItemFromFav(stock)

                }
                else{

                        icon.setImageResource(R.drawable.ic_favourite)
                        itemClickListener.addFavourite(stock)



                }


                Log.d("binder icon" , position.toString())
                Log.d("adapter " , adapterPosition.toString())
            }

            costOfStock.text = price.c.toString() + "$"
            var change_of_cost = price.c - price.pc
            if(change_of_cost < 0 ){

                changeOfCost.setTextColor(Color.parseColor("#B22424"))
                changeOfCost.text = "-$" + String.format("%.2f", (change_of_cost * -1)) + "(" + String.format("%.2f", (change_of_cost * -1) / price.c)  + "%)"
            }
            else{
                if(price.c == 0.0){
                    changeOfCost.text = "+$0.0(0.0%)"
                }
                else{
                    changeOfCost.setTextColor(Color.parseColor("#24B25D"))
                    changeOfCost.text = "+$" +String.format("%.2f", (change_of_cost))  + "(" +  String.format("%.2f", (change_of_cost) / price.c)  + "%)"
                }


            }


            if (!stock.logo.isNullOrEmpty()) {

                Picasso.get().load(stock.logo).into(logoOfCompany)
            }

            }

    }


}
interface OnFavouriteClickListener  {


    fun addFavourite(stock: Stock)
    fun isFavourite(stock: Stock): Boolean
    fun deleteItemFromFav(stock: Stock)
    fun getPrice(ticker: String): StockPrice

}


