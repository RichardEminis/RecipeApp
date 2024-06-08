package com.example.recipeapp.di

import com.example.recipeapp.RecipesRepository
import com.example.recipeapp.ui.recipe.favorites.FavoritesViewModel

class FavoritesViewModelFactory(
    private val recipesRepository: RecipesRepository,
): Factory<FavoritesViewModel> {

    override fun create(): FavoritesViewModel {
        return FavoritesViewModel(recipesRepository)
    }
}