package ui.recipe.recipeList

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.databinding.ItemRecipeBinding
import model.Recipe
import java.io.InputStream

class RecipeListAdapter(
    private val dataSet: List<Recipe>,
    private var itemClickListener: OnItemClickListener? = null
) : RecyclerView.Adapter<RecipeListAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(recipeId: Int)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemRecipeBinding.bind(view)
        fun bind(recipe: Recipe, itemClickListener: OnItemClickListener?) = with(binding) {
            tvRecipeName.text = recipe.title
            ivRecipeImage.setImageDrawable(
                getDrawableFromAssets(
                    recipe.imageUrl, itemView.context
                )
            )
            cvRecipeItem.setOnClickListener {
                itemClickListener?.onItemClick(recipe.id)
            }
        }

        private fun getDrawableFromAssets(
            imageUrl: String,
            context: Context
        ): Drawable? {
            return try {
                val inputStream: InputStream? = context.assets?.open(imageUrl)
                Drawable.createFromStream(inputStream, null)
            } catch (exception: Exception) {
                Log.e("mylog", "Error: $exception")
                null
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