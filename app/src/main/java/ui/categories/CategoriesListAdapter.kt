package ui.categories

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.IMAGE_URL
import com.example.recipeapp.R
import model.Category

class CategoriesListAdapter(
    private var itemClickListener: OnItemClickListener? = null,
) : RecyclerView.Adapter<CategoriesListAdapter.ViewHolder>() {

    var dataSet: List<Category> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
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
        holder.categoryItem.setOnClickListener {
            itemClickListener?.onItemClick(dataSet[position].id)
        }

        val imageUrl = IMAGE_URL + dataSet[position].imageUrl
        Glide.with(holder.context)
            .load(imageUrl)
            .placeholder(R.drawable.img_placeholder)
            .error(R.drawable.img_error)
            .into(holder.categoryImage)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }
}