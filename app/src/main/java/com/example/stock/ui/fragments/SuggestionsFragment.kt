package com.example.stock.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.stock.R

import com.example.stock.ui.adapter.ItemClicked
import com.example.stock.ui.adapter.SuggestionsFragmentAdapter
import kotlinx.android.synthetic.main.fragment_suggestions.*


class SuggestionsFragment : Fragment() , ItemClicked {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_suggestions, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val popularRequestList =
                listOf(
                        "Tesla",
                        "Google",
                        "Facebook",
                        "Apple",
                        "Monster",
                        "Microsoft",
                        "Amazon",
                        "Fox",
                        "Mastercard",
                        "Visa"
                )

        popular_rv.adapter = SuggestionsFragmentAdapter(popularRequestList , this)
        popular_rv.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL)


        searched_rv.adapter = SuggestionsFragmentAdapter(popularRequestList , this)
        searched_rv.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL)


    }


    override fun setSearchField(name: String) {
        var action = SuggestionsFragmentDirections.actionSuggestionsFragmentToStocksFragment2(name)
        findNavController().navigate(action)

    }

}