package ui.recipe.recipe

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.databinding.ItemIngredientsBinding
import model.Ingredient
import java.math.BigDecimal
import java.math.RoundingMode

class IngredientsAdapter(
    private var dataSet: List<Ingredient> = emptyList()
) : RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    private var portionsCount: Int = 1

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
        val ingredient = dataSet[position]
        val multipliedQuantity = BigDecimal(ingredient.quantity).multiply(BigDecimal(portionsCount))
        val displayQuantity = if (multipliedQuantity.scale() <= 0) {
            multipliedQuantity.toInt().toString()
        } else {
            multipliedQuantity.setScale(2, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString()
        }
        holder.bind(ingredient, displayQuantity)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun ingredientsUpdateDataSet(newDataSet: List<Ingredient>, portionsCount: Int) {
        this.dataSet = newDataSet
        this.portionsCount = portionsCount
        notifyDataSetChanged()
    }
}