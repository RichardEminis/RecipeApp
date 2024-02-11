package com.example.recipeapp

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import model.Category
import model.Ingredient
import model.Recipe
import java.io.InputStream

class RecipeListAdapter(
    private val dataSet: List<Recipe>,
    private val fragment: RecipesListFragment,
    private var itemClickListener: RecipeListAdapter.OnItemClickListener? = null
) : RecyclerView.Adapter<RecipeListAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(recipeId: Int)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recipeItem: CardView = itemView.findViewById(R.id.cvRecipeItem)
        var recipeImage: ImageView = itemView.findViewById(R.id.ivRecipeImage)
        var recipeName: TextView = itemView.findViewById(R.id.tvRecipeName)
    }

    private fun getDrawableFromAssets(imageUrl: String): Drawable? {
        try {
            val inputStream: InputStream? = fragment.context?.assets?.open(imageUrl)
            val drawable = Drawable.createFromStream(inputStream, null)
            return drawable
        } catch (exception: Exception) {
            Log.e("mylog", "Error: $exception")
            return null
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): RecipeListAdapter.ViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        return RecipeListAdapter.ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: RecipeListAdapter.ViewHolder, position: Int) {
        holder.recipeName.text = dataSet[position].title
        holder.recipeImage.setImageDrawable(getDrawableFromAssets(dataSet[position].imageUrl))
        holder.recipeItem.setOnClickListener {
            itemClickListener?.onItemClick(dataSet[position].id)
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun setOnItemClickListener(listener: RecipeListAdapter.OnItemClickListener) {
        itemClickListener = listener
    }
}