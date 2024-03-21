package com.example.recipeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import model.Recipe

class MethodAdapter(
    private val dataSet: Recipe
) : RecyclerView.Adapter<MethodAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var methodText: TextView = itemView.findViewById(R.id.tvMethodText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.item_method, parent, false)
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.methodText.text = dataSet.method[position]
    }

    override fun getItemCount(): Int {
        return dataSet.method.size
    }
}