package com.example.recipeapp.ui.recipe.recipeList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.RecipesRepository
import com.example.recipeapp.model.Recipe
import kotlinx.coroutines.launch

data class RecipesUiState(
    val recipes: List<Recipe> = emptyList()
)

class RecipesListViewModel(private val recipesRepository: RecipesRepository) : ViewModel() {

    private val _recipesUiState = MutableLiveData(RecipesUiState())
    val recipesUiState: MutableLiveData<RecipesUiState>
        get() = _recipesUiState

    fun loadRecipesByCategoryId(categoryId: Int) {
        viewModelScope.launch {
            val recipes = recipesRepository.getRecipesWithFavorites(categoryId)
            _recipesUiState.value = recipesUiState.value?.copy(recipes = recipes)
        }
    }
}