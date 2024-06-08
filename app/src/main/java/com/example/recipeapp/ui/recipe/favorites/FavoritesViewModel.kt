package com.example.recipeapp.ui.recipe.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.RecipesRepository
import com.example.recipeapp.model.Recipe
import kotlinx.coroutines.launch

data class FavoritesUiState(
    val favoriteRecipes: List<Recipe> = emptyList()
)

class FavoritesViewModel(private val recipesRepository: RecipesRepository) : ViewModel() {

    private val _favoritesUiState = MutableLiveData(FavoritesUiState())
    val favoritesUiState: LiveData<FavoritesUiState>
        get() = _favoritesUiState

    fun loadFavorites() {
        viewModelScope.launch {
            val recipes = recipesRepository.getFavoriteRecipes()
            _favoritesUiState.value = favoritesUiState.value?.copy(favoriteRecipes = recipes)
        }
    }
}