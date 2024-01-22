package com.example.recipeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import model.Category

class CategoriesListAdapter(
    private val dataSet: List<Category>,
    private val fragment: CategoriesListFragment
) : RecyclerView.Adapter<CategoriesListAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val categoryItem: CardView = itemView.findViewById(R.id.cvCategoryItem)
        val categoryImage: ImageView = itemView.findViewById(R.id.ivCategoryImage)
        val categoryName: TextView = itemView.findViewById(R.id.tvCategoryName)
        val categoryDescription: TextView = itemView.findViewById(R.id.tvCategoryDescription)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoriesListAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: CategoriesListAdapter.ViewHolder, position: Int) {
        val viewHolder = TODO()
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}