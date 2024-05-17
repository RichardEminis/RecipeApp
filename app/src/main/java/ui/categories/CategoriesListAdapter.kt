package ui.categories

import android.annotation.SuppressLint
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
import com.example.recipeapp.R
import data.STUB
import model.Category
import java.io.InputStream

class CategoriesListAdapter(
    private var categories: List<Category>,
    private var itemClickListener: OnItemClickListener? = null,
) : RecyclerView.Adapter<CategoriesListAdapter.ViewHolder>() {

    var dataSet: List<Category>
        get() = categories
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            categories = value
            notifyDataSetChanged()
        }

    interface OnItemClickListener {
        fun onItemClick(categoryId: Int)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val categoryItem: CardView = itemView.findViewById(R.id.cvCategoryItem)
        var categoryImage: ImageView = itemView.findViewById(R.id.ivCategoryImage)
        var categoryName: TextView = itemView.findViewById(R.id.tvCategoryName)
        val categoryDescription: TextView = itemView.findViewById(R.id.tvCategoryDescription)
        val context: Context = view.context
    }

    private fun getDrawableFromAssets(context: Context, imageUrl: String): Drawable? {
        try {
                val inputStream: InputStream? = context.assets?.open(imageUrl)
            val drawable = Drawable.createFromStream(inputStream, null)
            return drawable
        } catch (exception: Exception) {
            Log.e("mylog", "Error: $exception")
            return null
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.categoryName.text = dataSet[position].title
        holder.categoryDescription.text = dataSet[position].description
        holder.categoryImage.setImageDrawable(getDrawableFromAssets(holder.context, dataSet[position].imageUrl))
        holder.categoryItem.setOnClickListener {
            itemClickListener?.onItemClick(dataSet[position].id)
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }
}