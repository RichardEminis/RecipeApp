package com.example.recipeapp.ui.recipe.recipeList

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.IMAGE_URL
import com.example.recipeapp.R
import com.example.recipeapp.databinding.ItemRecipeBinding
import com.example.recipeapp.model.Recipe
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class RecipeListAdapter @Inject constructor(
    private var itemClickListener: OnItemClickListener? = null
) : RecyclerView.Adapter<RecipeListAdapter.ViewHolder>() {

    var dataSet: List<Recipe> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    interface OnItemClickListener {
        fun onItemClick(recipeId: Int)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemRecipeBinding.bind(view)
        fun bind(recipe: Recipe, itemClickListener: OnItemClickListener?) = with(binding) {
            tvRecipeName.text = recipe.title

            val imageUrl = IMAGE_URL + recipe.imageUrl
            Glide.with(itemView.context)
                .load(imageUrl)
                .placeholder(R.drawable.img_placeholder)
                .error(R.drawable.img_error)
                .into(ivRecipeImage)

            cvRecipeItem.setOnClickListener {
                itemClickListener?.onItemClick(recipe.id)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position], itemClickListener)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }
}