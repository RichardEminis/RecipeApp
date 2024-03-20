package com.example.recipeapp

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.databinding.FragmentRecipeBinding
import data.STUB
import model.Recipe

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

        val recipe = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(ARG_RECIPE, Recipe::class.java)
        } else {
            arguments?.getParcelable(ARG_RECIPE)
        }

        val mDividerItemDecoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        ContextCompat.getDrawable(
            requireContext(),
            androidx.appcompat.R.drawable.abc_list_divider_material
        )
            ?.let { mDividerItemDecoration.setDrawable(it) }
        binding.rvIngredients.addItemDecoration(mDividerItemDecoration)

        initUI(recipe)
        initRecycler()
    }

    private fun initUI(recipe: Recipe?) {
        binding.recipeListImage.setImageResource(R.drawable.burger)

        if (recipe != null) {
            binding.recipeText.text = recipe.title
        }
    }

    private fun initRecycler() {
        val ingredientsAdapter =
            IngredientsAdapter(STUB.getRecipeById(recipeId))
        binding.rvIngredients.adapter = ingredientsAdapter

        val methodAdapter = MethodAdapter(STUB.getRecipeById(recipeId))
        binding.rvMethod.adapter = methodAdapter
    }
}