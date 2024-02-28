package com.example.recipeapp

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import model.Ingredient
import model.Recipe

class IngredientsAdapter(
    private val dataSet: List<Recipe>
): RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}