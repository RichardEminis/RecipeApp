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
        var ingredientsDescription: TextView = itemView.findViewById(R.id.tvIngredientsDescription)
        var ingredientsUnitOfMeasure: TextView =
            itemView.findViewById(R.id.tvIngredientsUnitOfMeasure)
        var ingredientsQuantity: TextView = itemView.findViewById(R.id.tvIngredientsQuantity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.item_ingredients, parent, false)
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ingredientsDescription.text = dataSet.listOfIngredients[position].description
        holder.ingredientsUnitOfMeasure.text = dataSet.listOfIngredients[position].unitOfMeasure
        holder.ingredientsQuantity.text = dataSet.listOfIngredients[position].quantity
    }

    override fun getItemCount(): Int {
        return dataSet.listOfIngredients.size
    }
}