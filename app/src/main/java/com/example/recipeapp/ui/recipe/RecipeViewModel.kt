package com.example.recipeapp.ui.recipe

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.IMAGE_URL
import com.example.recipeapp.RecipesRepository
import kotlinx.coroutines.launch
import com.example.recipeapp.model.Recipe

data class RecipeUiState(
    var recipe: Recipe? = null,
    var portionsCount: Int = 1,
    var isFavorite: Boolean = false,
    var recipeImage: String? = null,
)

class RecipeViewModel(application: Application) : AndroidViewModel(application) {
    private val _recipeUiState = MutableLiveData(RecipeUiState())
    val recipeUiState: LiveData<RecipeUiState>
        get() = _recipeUiState

    private val repository = RecipesRepository(application)

    fun loadRecipe(recipeId: Int) {
        viewModelScope.launch {
            val recipe = repository.getRecipeByIdFromCache(recipeId)
            val imageUrl = IMAGE_URL + recipe?.imageUrl

            _recipeUiState.value = recipeUiState.value?.copy(
                recipe = recipe,
                isFavorite = recipe?.isFavoriteRecipe ?: false,
                recipeImage = imageUrl
            )
        }
    }

    fun onFavoritesClicked() {
        val recipe = _recipeUiState.value?.recipe ?: return
        val isFavorite = !recipe.isFavoriteRecipe

        viewModelScope.launch {
            repository.updateFavoriteStatus(recipe.id, isFavorite)
            _recipeUiState.value = recipeUiState.value?.copy(
                recipe = recipe.copy(isFavoriteRecipe = isFavorite),
                isFavorite = isFavorite
            )
        }
    }

    fun updatePortionsCount(portionsCount: Int) {
        _recipeUiState.value?.let { currentState ->
            _recipeUiState.value = currentState.copy(portionsCount = portionsCount)
        }
    }
}