package com.example.stock.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stock.R

class SuggestionsFragmentAdapter(private val reqs: List<String> , private val itemClicked: ItemClicked): RecyclerView.Adapter<SuggestionsFragmentAdapter.SuggestionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionsFragmentAdapter.SuggestionViewHolder {
        return SuggestionsFragmentAdapter.SuggestionViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.popular_req_item, parent, false))
    }

    override fun onBindViewHolder(holder: SuggestionsFragmentAdapter.SuggestionViewHolder, position: Int) {
        holder.bind(reqs[position] , itemClicked)
    }

    override fun getItemCount(): Int {
        return reqs.size
    }


    class SuggestionViewHolder(view: View): RecyclerView.ViewHolder(view){
        private var suggestion:TextView = view.findViewById(R.id.req_popular)


        fun bind(name: String, itemClicked: ItemClicked){

            suggestion.text = name
            suggestion.setOnClickListener {
                itemClicked.setSearchField(name)
            }
        }
    }


    }

interface ItemClicked  {
    fun setSearchField(name: String)

}