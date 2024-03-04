package com.example.recipeapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.databinding.FragmentRecipeBinding
import model.Recipe

class IngredientsAdapter(
    private val dataSet: Recipe
) : RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    class ViewHolder(private val binding: FragmentRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var ingredients: TextView = itemView.findViewById(R.id.rvIngredients)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            FragmentRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ingredients.text = dataSet.listOfIngredients[position].description
    }

    override fun getItemCount(): Int {
        return dataSet.method.size
    }
}