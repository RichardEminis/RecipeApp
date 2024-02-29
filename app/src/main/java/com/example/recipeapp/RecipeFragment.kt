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


    private var recyclerView: RecyclerView? = null

    private var categoryId: Int? = 0

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

        if (recipe != null) {
            binding.recipeText.text = recipe.title
        }

        val mDividerItemDecoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        ContextCompat.getDrawable(requireContext(), androidx.appcompat.R.drawable.abc_list_divider_material)
            ?.let { mDividerItemDecoration.setDrawable(it) }
        binding.rvIngredients.addItemDecoration(mDividerItemDecoration)

        initUI()
        initRecycler()
    }

    private fun initUI() {
        binding.recipeListImage.setImageResource(R.drawable.burger)

        binding.recipeText.text = STUB.getRecipeById(0).title
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