package com.example.recipeapp.ui.recipe.recipeList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.IMAGE_URL
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentRecipesListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipesListFragment : Fragment() {

    private var recyclerView: RecyclerView? = null
    private val args: RecipesListFragmentArgs by navArgs()
    private lateinit var viewModel: RecipesListViewModel
    private var recipeListAdapter: RecipeListAdapter = RecipeListAdapter()

    private var _binding: FragmentRecipesListBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for ActivityMainBinding must not be null")

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

        val category = args.category
        binding.recipeListText.text = category.title

        val imageUrl = IMAGE_URL + category.imageUrl
        Glide.with(this)
            .load(imageUrl)
            .placeholder(R.drawable.img_placeholder)
            .error(R.drawable.img_error)
            .into(binding.recipeListImage)

        viewModel.loadRecipesByCategoryId(category.id)

        viewModel.recipesUiState.observe(viewLifecycleOwner) { uiState ->
            if (uiState != null) {
                recipeListAdapter.dataSet = uiState.recipes
            }
        }

        initRecycler()
    }

    private fun openRecipeByRecipeId(recipeId: Int) {
        findNavController().navigate(
            RecipesListFragmentDirections.actionRecipesListFragmentToRecipeFragment(
                recipeId
            )
        )
    }

    private fun initRecycler() {
        recyclerView = binding.rvRecipes
        recyclerView?.adapter = recipeListAdapter

        recipeListAdapter.setOnItemClickListener(object : RecipeListAdapter.OnItemClickListener {
            override fun onItemClick(recipeId: Int) {
                openRecipeByRecipeId(recipeId)
            }
        })
    }
}