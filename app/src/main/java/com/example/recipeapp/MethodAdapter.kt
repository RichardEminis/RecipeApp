package com.example.recipeapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.databinding.FragmentRecipeBinding
import model.Recipe

class MethodAdapter(
    private val dataSet: Recipe
) : RecyclerView.Adapter<MethodAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            FragmentRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val step = listOf(dataSet[position])
        holder.bind(step)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    class ViewHolder(private val binding: FragmentRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(method: List<Recipe>) {
            val adapter = MethodAdapter(method)
            binding.rvMethod.adapter = adapter
        }
    }
}