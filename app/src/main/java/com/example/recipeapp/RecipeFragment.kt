package com.example.recipeapp

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.databinding.FragmentRecipeBinding
import com.example.recipeapp.databinding.FragmentRecipesListBinding
import data.STUB
import model.Recipe

class RecipeFragment : Fragment() {

    private var recyclerView: RecyclerView? = null

    private var categoryId: Int? = 0

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recipe = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(ARG_RECIPE, Recipe::class.java)
        } else {
            arguments?.getParcelable(ARG_RECIPE)
        }

        if (recipe != null) {
            binding.recipeText.text = recipe.title
        }
    }

    private fun initUI() {
        binding.recipeListImage.setImageResource(R.drawable.burger)

        binding.recipeText.text = "TODO"
    }

    private fun initRecycler() {
        val ingredientsAdapter =
            STUB.getRecipesByCategoryId(categoryId)?.let { IngredientsAdapter(it) }
        recyclerView = binding.rvIngredients
        recyclerView?.adapter = ingredientsAdapter

        val methodAdapter = STUB.getRecipesByCategoryId(categoryId)?.let { MethodAdapter(it) }
        recyclerView = binding.rvMethod
        recyclerView?.adapter = methodAdapter
    }
}