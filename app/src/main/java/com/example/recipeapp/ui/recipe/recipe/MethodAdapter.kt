package com.example.recipeapp.ui.recipe.recipe

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.databinding.ItemMethodBinding

class MethodAdapter(
    private var dataSet: List<String> = emptyList()
) : RecyclerView.Adapter<MethodAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemMethodBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(method: String, position: Int) {
            binding.tvMethodText.text = "${position + 1}. $method"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMethodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val method = dataSet[position]
        holder.bind(method, position)
    }

    override fun getItemCount(): Int {
        return dataSet.size ?: 0
    }

    @SuppressLint("NotifyDataSetChanged")
    fun methodUpdateDataSet(newDataSet: List<String>) {
        this.dataSet = newDataSet
        notifyDataSetChanged()
    }
}