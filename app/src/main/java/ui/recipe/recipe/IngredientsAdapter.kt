package ui.recipe.recipe

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.databinding.ItemIngredientsBinding
import model.Ingredient
import model.Recipe
import java.math.BigDecimal

class IngredientsAdapter(
    private var dataSet: Recipe,
    private var portionsCount: Int = 1,
) : RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemIngredientsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ingredient: Ingredient, displayQuantity: String) {
            binding.tvIngredientsDescription.text = ingredient.description
            binding.tvIngredientsUnitOfMeasure.text = ingredient.unitOfMeasure
            binding.tvIngredientsQuantity.text = displayQuantity
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemIngredientsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("DefaultLocale")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ingredient = dataSet.listOfIngredients[position]
        val multipliedQuantity = BigDecimal(ingredient.quantity).multiply(BigDecimal(portionsCount))
        val displayQuantity = if (multipliedQuantity.scale() <= 0) {
            multipliedQuantity.toInt().toString()
        } else {
            multipliedQuantity.setScale(1).toString()
        }
        holder.bind(ingredient, displayQuantity)
    }

    override fun getItemCount(): Int {
        return dataSet.listOfIngredients.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updatePortionsCount(portions: Int) {
        portionsCount = portions
        notifyDataSetChanged()
    }
}