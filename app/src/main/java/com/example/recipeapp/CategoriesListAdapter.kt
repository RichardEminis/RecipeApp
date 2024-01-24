package com.example.recipeapp

import android.content.Context
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
import java.io.InputStream

class CategoriesListAdapter(
    private val dataSet: List<Category>,
    private val fragment: CategoriesListFragment
) : RecyclerView.Adapter<CategoriesListAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val context: Context = view.context
        val categoryItem: CardView = itemView.findViewById(R.id.cvCategoryItem)
        var categoryImage: ImageView = itemView.findViewById(R.id.ivCategoryImage)
        var categoryName: TextView = itemView.findViewById(R.id.tvCategoryName)
        val categoryDescription: TextView = itemView.findViewById(R.id.tvCategoryDescription)
    }

    private fun getDrawableFromAssets(imageUrl: String, context: Context): Drawable? {
        try {
            val inputStream: InputStream = context.assets.open(imageUrl)
            val drawable = Drawable.createFromStream(inputStream, null)
            return drawable
        } catch (exception: Exception) {
            Log.e("mylog", "Error: $exception")
            return null
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoriesListAdapter.ViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.categoryName.text = dataSet[position].title
        holder.categoryDescription.text = dataSet[position].description
        holder.categoryImage.setImageDrawable(
            getDrawableFromAssets(
                dataSet[position].imageUrl,
                holder.context
            )
        )
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}