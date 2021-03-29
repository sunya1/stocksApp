package com.example.stock.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import com.example.stock.R
import com.example.stock.model.Stock
import com.example.stock.ui.viewmodels.SharedViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_stocks.*
import java.util.*
import kotlin.collections.ArrayList


class SearchFragment : Fragment(R.layout.fragment_search) {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    lateinit var helper: Helper


    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            helper = context as Helper
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement MyListener")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.suggestion.observe(viewLifecycleOwner, {
            if(it != null){


                searh_field.setQuery(it , false)
            }

        })


        setSearchClickListener(sharedViewModel.stocks)




    }
    fun setSearchClickListener(stocks: LiveData<List<Stock>>) {
        searh_field.setOnClickListener {
            helper.goToNext()
        }
        searh_field.setOnSearchClickListener {
            helper.goToNext()
        }
        searh_field.setOnCloseListener {
            searh_field.setQuery("" , false )
            helper.goBack()


            return@setOnCloseListener false
        }
        searh_field.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searh_field.setQuery("" , false )
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                if (newText != null) {
                    filter(newText, stocks)
                    helper.goBack()

                }


                if(newText.isNullOrEmpty()){
                    helper.goToNext()
                }

                return false
            }

        })


    }
    fun filter(ticker: String, stocks: LiveData<List<Stock>>){

        var filtered: ArrayList<Stock> = ArrayList()

        if(ticker.length == 0){
            filtered.clear();

        }
        var i = 0
        stocks.observe(viewLifecycleOwner ,{
            for(stock in it){

                if(stock == null){
                    break
                }
                val check = stock.ticker
                val check1 = stock.name


                if(check.toLowerCase().contains(ticker.toLowerCase()) || check1.toLowerCase().contains(
                                ticker.toLowerCase()
                        )){



                    filtered.add(stock)
                }


            }

        })



        sharedViewModel.setFStock(filtered)






    }




}
interface Helper{

    fun goToNext()
    fun goBack()
}

