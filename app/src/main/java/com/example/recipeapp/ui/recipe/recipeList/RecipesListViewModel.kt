package com.example.recipeapp.ui.recipe.recipeList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.repository.RecipesRepository
import com.example.recipeapp.model.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RecipesUiState(
    val recipes: List<Recipe> = emptyList()
)

@HiltViewModel
class RecipesListViewModel @Inject constructor(private val recipesRepository: RecipesRepository) :
    ViewModel() {

    private val _recipesUiState = MutableLiveData(RecipesUiState())
    val recipesUiState: MutableLiveData<RecipesUiState>
        get() = _recipesUiState

    fun loadRecipesByCategoryId(categoryId: Int) {
        viewModelScope.launch {
            val cachedRecipes = recipesRepository.getRecipesFromCache(categoryId)
            _recipesUiState.value = recipesUiState.value?.copy(recipes = cachedRecipes)

            val networkRecipes = recipesRepository.getRecipes(categoryId)
            _recipesUiState.value = recipesUiState.value?.copy(recipes = networkRecipes)
        }
    }
}