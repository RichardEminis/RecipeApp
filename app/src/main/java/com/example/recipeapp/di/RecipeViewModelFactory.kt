package com.example.recipeapp.di

import com.example.recipeapp.RecipesRepository
import com.example.recipeapp.ui.recipe.RecipeViewModel

class RecipeViewModelFactory(
    private val recipesRepository: RecipesRepository,
): Factory<RecipeViewModel> {

    override fun create(): RecipeViewModel {
        return RecipeViewModel(recipesRepository)
    }
}