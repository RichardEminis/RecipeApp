package com.example.recipeapp

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeapp.databinding.FragmentRecipeBinding
import data.STUB
import model.Recipe
import java.io.InputStream

class RecipeFragment : Fragment() {

    private var recipeId: Int = 0

    private val binding: FragmentRecipeBinding by lazy {
        FragmentRecipeBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()

        initRecycler()
    }

    private fun initUI() {
        val recipe = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(ARG_RECIPE, Recipe::class.java)
        } else {
            arguments?.getParcelable(ARG_RECIPE)
        }

        if (recipe != null) {
            recipeId = recipe.id
        }

        val drawable = recipe?.imageUrl?.let { getDrawableFromAssets(it, requireContext()) }
        binding.recipeImage.setImageDrawable(drawable)

        if (recipe != null) {
            binding.recipeText.text = recipe.title
        }
    }

    private fun initRecycler() {
        val ingredientsAdapter = IngredientsAdapter(STUB.getRecipeById(recipeId))
        val methodAdapter = MethodAdapter(STUB.getRecipeById(recipeId))

        val linearLayoutManagerIngredients = LinearLayoutManager(context)
        binding.rvIngredients.layoutManager = linearLayoutManagerIngredients
        binding.rvIngredients.adapter = ingredientsAdapter

        val linearLayoutManagerMethod = LinearLayoutManager(context)
        binding.rvMethod.layoutManager = linearLayoutManagerMethod
        binding.rvMethod.adapter = methodAdapter

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(
                seekBar: SeekBar,
                progress: Int,
                fromUser: Boolean
            ) {
                binding.portionsValue.text = (progress + 1).toString()
                ingredientsAdapter.updateIngredients(progress + 1)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
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