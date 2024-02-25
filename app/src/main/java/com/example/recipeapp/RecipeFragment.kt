package com.example.recipeapp

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.example.recipeapp.databinding.FragmentRecipeBinding
import com.example.recipeapp.databinding.FragmentRecipesListBinding
import model.Recipe

class RecipeFragment : Fragment() {

    private var _binding: FragmentRecipeBinding? = null

    private val binding: FragmentRecipeBinding by lazy {
        FragmentRecipeBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recipe: Recipe? = arguments?.getParcelable(ARG_RECIPE, Recipe::class.java)

        if (recipe != null) {
            binding.recipeText.text = recipe.title
        }
    }
}