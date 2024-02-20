package com.example.recipeapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.databinding.FragmentRecipesListBinding
import data.STUB

class RecipesListFragment : Fragment() {

    private var recyclerView: RecyclerView? = null

    private var _binding: FragmentRecipesListBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for ActivityMainBinding must not be null")

    private var categoryName: String? = null
    private var categoryImageUrl: String? = null
    private var categoryId: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryId = requireArguments().getInt(ARG_CATEGORY_ID)
        categoryName = requireArguments().getString(ARG_CATEGORY_NAME)
        categoryImageUrl = requireArguments().getString(ARG_CATEGORY_IMAGE_URL)

        initRecycler()
    }

    private fun openRecipeByRecipeId(recipeId: Int) {
        parentFragmentManager.commit {
            replace<RecipeFragment>(R.id.mainContainer)
            setReorderingAllowed(true)
            addToBackStack(null)
        }

        val recipeName = STUB.getRecipesByCategoryId(categoryId)?.get(recipeId)?.title
        val recipeImageUrl = STUB.getRecipesByCategoryId(categoryId)?.get(recipeId)?.imageUrl
        val bundle = bundleOf(
            ARG_RECIPE_ID to recipeId,
            ARG_RECIPE_NAME to recipeName,
            ARG_RECIPE_IMAGE_URL to recipeImageUrl
        )

        parentFragmentManager.commit {
            setReorderingAllowed(true)
            add<RecipeFragment>(R.id.mainContainer, args = bundle)
        }
    }

    private fun initRecycler() {
        val adapter = STUB.getRecipesByCategoryId(categoryId)?.let { RecipeListAdapter(it) }
        recyclerView = binding.rvRecipes
        recyclerView?.adapter = adapter

        adapter?.setOnItemClickListener(object : RecipeListAdapter.OnItemClickListener {
            override fun onItemClick(recipeId: Int) {
                openRecipeByRecipeId(recipeId)
            }
        })
    }
}