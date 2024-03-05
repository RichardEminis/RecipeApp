package com.example.recipeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import model.Recipe

class IngredientsAdapter(
    private val dataSet: Recipe
) : RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var ingredients: TextView = itemView.findViewById(R.id.tvIngredientsText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.item_ingredients, parent, false)
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ingredients.text = dataSet.listOfIngredients[position].description
    }

    override fun getItemCount(): Int {
        return dataSet.listOfIngredients.size
    }
}