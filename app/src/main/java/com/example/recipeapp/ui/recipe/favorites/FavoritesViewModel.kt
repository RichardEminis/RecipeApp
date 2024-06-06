package com.example.recipeapp.ui.recipe.favorites

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.FAVORITES_SHARED_PREFERENCES
import com.example.recipeapp.KEY_FAVORITES
import com.example.recipeapp.RecipesRepository
import kotlinx.coroutines.launch
import com.example.recipeapp.model.Recipe

data class FavoritesUiState(
    val favoriteRecipes: List<Recipe> = emptyList()
)

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    private val _favoritesUiState = MutableLiveData(FavoritesUiState())
    val favoritesUiState: LiveData<FavoritesUiState>
        get() = _favoritesUiState

    private val repository = RecipesRepository(application)

    fun loadFavorites() {
        viewModelScope.launch {
            val recipes = repository.getFavoriteRecipes()
            _favoritesUiState.value = favoritesUiState.value?.copy(favoriteRecipes = recipes)
        }
    }
}