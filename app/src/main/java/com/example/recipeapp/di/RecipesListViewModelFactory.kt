package com.example.recipeapp.di

import com.example.recipeapp.repository.RecipesRepository
import com.example.recipeapp.ui.recipe.recipeList.RecipesListViewModel

class RecipesListViewModelFactory(
    private val recipesRepository: RecipesRepository,
    ): Factory<RecipesListViewModel> {

    override fun create(): RecipesListViewModel {
        return RecipesListViewModel(recipesRepository)
    }
}