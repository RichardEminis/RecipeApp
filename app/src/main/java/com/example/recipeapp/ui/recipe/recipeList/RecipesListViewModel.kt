package com.example.recipeapp.ui.recipe.recipeList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.RecipesRepository
import com.example.recipeapp.model.Recipe
import kotlinx.coroutines.launch

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
            val recipes = repository.getRecipesWithFavorites(categoryId)
            _recipesUiState.value = recipesUiState.value?.copy(recipes = recipes)
        }
    }
}