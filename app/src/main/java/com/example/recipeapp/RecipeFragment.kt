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
import android.widget.ImageButton
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeapp.databinding.FragmentRecipeBinding
import data.STUB
import model.Recipe
import java.io.InputStream

class RecipeFragment : Fragment() {

    private val binding: FragmentRecipeBinding by lazy {
        FragmentRecipeBinding.inflate(layoutInflater)
    }

    private lateinit var btnFavorite: ImageButton

    private var recipeId: Int = 0

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

    @SuppressLint("UseCompatLoadingForDrawables")
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

        btnFavorite = binding.ibFavorite

        val favoritesSet = getFavorites()
        val isFavorite = favoritesSet?.contains(recipeId.toString())

        if (isFavorite == true) {
            binding.ibFavorite.setImageResource(R.drawable.ic_heart)
        } else {
            binding.ibFavorite.setImageResource(R.drawable.ic_heart_empty)
        }

        btnFavorite.setOnClickListener {
            if (isFavorite == true) {
                favoritesSet.remove(recipeId.toString())
                btnFavorite.setImageResource(R.drawable.ic_heart_empty)
            } else {
                favoritesSet?.add(recipeId.toString())
                btnFavorite.setImageResource(R.drawable.ic_heart)
            }
            if (favoritesSet != null) {
                saveFavorites(favoritesSet)
            }
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
                binding.portionsValue.text = progress.toString()
                ingredientsAdapter.updateIngredients(progress)
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

    private fun saveFavorites(favoritesSet: Set<String>) {
        val sharedPrefs = requireActivity().getSharedPreferences(
            "FavoritesSharedPreferences", Context.MODE_PRIVATE
        )
        val editor = sharedPrefs.edit()
        editor.putStringSet("favoriteRecipes", favoritesSet)
        editor.apply()
    }

    private fun getFavorites(): MutableSet<String>? {
        val sharedPrefs = requireActivity().getSharedPreferences(
            "FavoritesSharedPreferences", Context.MODE_PRIVATE
        )
        return sharedPrefs.getStringSet("favoriteRecipes", HashSet())?.toMutableSet()
    }
}