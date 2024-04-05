package com.example.recipeapp

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.databinding.FragmentFavoritesBinding
import com.example.recipeapp.databinding.FragmentRecipeBinding
import data.STUB

class FavoritesFragment : Fragment() {

    private var recyclerView: RecyclerView? = null

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

        initRecycler()
    }

    private fun initRecycler() {
        val favoritesSet = getFavorites() ?: HashSet()
        val adapter =
            RecipeListAdapter(STUB.getRecipesByIds(favoritesSet.map { it.toInt() }.toSet()))
        recyclerView = binding.rvFavorites

        if (favoritesSet.isEmpty()) {
            binding.emptyFavorites.visibility = View.VISIBLE
            recyclerView?.visibility = View.GONE
        } else {
            binding.emptyFavorites.visibility = View.GONE
            recyclerView?.visibility = View.VISIBLE
            recyclerView?.adapter = adapter

            adapter.setOnItemClickListener(object : RecipeListAdapter.OnItemClickListener {
                override fun onItemClick(recipeId: Int) {
                    openRecipesByCategoryId(recipeId)
                }
            })
        }
    }

    private fun openRecipesByCategoryId(recipeId: Int) {

        val recipe = STUB.getRecipeById(recipeId)

        val bundle = bundleOf(ARG_RECIPE to recipe)

        parentFragmentManager.commit {
            setReorderingAllowed(true)
            add<RecipeFragment>(R.id.mainContainer, args = bundle)
        }
    }

    private fun getFavorites(): MutableSet<String>? {
        val sharedPrefs = requireActivity().getSharedPreferences(
            "FavoritesSharedPreferences", Context.MODE_PRIVATE
        )
        return sharedPrefs.getStringSet("favoriteRecipes", HashSet())?.toMutableSet()
    }
}