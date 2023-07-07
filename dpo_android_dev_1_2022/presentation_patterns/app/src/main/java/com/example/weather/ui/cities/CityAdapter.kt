package com.example.weather.ui.cities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class CityAdapter(private val cities: List<String>, private val onItemClick: (String) -> Unit) :
    RecyclerView.Adapter<CityAdapter.ViewHolder>() {

    class ViewHolder(itemView: View, val onItemClick: (String) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(android.R.id.text1)

        fun bind(city: String) {
            textView.text = city
            itemView.setOnClickListener { onItemClick(city) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return ViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cities[position])
    }

    override fun getItemCount(): Int = cities.size
}
