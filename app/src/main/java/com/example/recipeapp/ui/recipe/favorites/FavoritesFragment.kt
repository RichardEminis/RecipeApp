package com.example.recipeapp.ui.recipe.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.databinding.FragmentFavoritesBinding
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.ui.recipe.recipeList.RecipeListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {
    private var recyclerView: RecyclerView? = null
    private val viewModel: FavoritesViewModel by viewModels()
    private var recipeListAdapter: RecipeListAdapter = RecipeListAdapter()

    private val binding: FragmentFavoritesBinding by lazy {
        FragmentFavoritesBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadFavorites()

        viewModel.favoritesUiState.observe(viewLifecycleOwner) { state ->
            state?.let { initRecycler(it.favoriteRecipes) }
            recipeListAdapter.dataSet = state.favoriteRecipes
        }
    }

    private fun initRecycler(favoritesSet: List<Recipe>) {
        recyclerView = binding.rvFavorites

        if (favoritesSet.isEmpty()) {
            binding.emptyFavorites.visibility = View.VISIBLE
            recyclerView?.visibility = View.GONE
        } else {
            binding.emptyFavorites.visibility = View.GONE
            recyclerView?.visibility = View.VISIBLE
            recyclerView?.adapter = recipeListAdapter

            recipeListAdapter.setOnItemClickListener(object :
                RecipeListAdapter.OnItemClickListener {
                override fun onItemClick(recipeId: Int) {
                    findNavController().navigate(
                        FavoritesFragmentDirections.actionFavoritesFragmentToRecipeFragment(recipeId)
                    )
                }
            })
        }
    }
}