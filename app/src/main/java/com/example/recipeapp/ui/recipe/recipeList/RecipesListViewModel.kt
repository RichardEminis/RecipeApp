package com.example.recipeapp.ui.recipe.recipeList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.RecipesRepository
import kotlinx.coroutines.launch
import com.example.recipeapp.model.Recipe

data class RecipesUiState(
    val recipes: List<Recipe> = emptyList()
)

class RecipesListViewModel(application: Application) : AndroidViewModel(application){

    private val _recipesUiState = MutableLiveData(RecipesUiState())
    val recipesUiState: MutableLiveData<RecipesUiState>
        get() = _recipesUiState

    private val repository = RecipesRepository(application)

    fun loadRecipesByCategoryId(categoryId: Int) {
        viewModelScope.launch {
            val cachedRecipes = repository.getRecipesFromCache(categoryId)
            _recipesUiState.value = _recipesUiState.value?.copy(recipes = cachedRecipes)

            val recipes = repository.getRecipes(categoryId)
            _recipesUiState.value = _recipesUiState.value?.copy(recipes = recipes)

            repository.saveRecipesToCache(recipes)
        }
    }
}