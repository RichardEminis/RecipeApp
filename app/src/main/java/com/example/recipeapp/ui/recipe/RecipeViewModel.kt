package com.example.recipeapp.ui.recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.IMAGE_URL
import com.example.recipeapp.RecipesRepository
import com.example.recipeapp.model.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
data class RecipeUiState @Inject constructor(
    var recipe: Recipe? = null,
    var portionsCount: Int = 1,
    var isFavorite: Boolean = false,
    var recipeImage: String? = null,
)

class RecipeViewModel(private val recipesRepository: RecipesRepository) : ViewModel() {
    private val _recipeUiState = MutableLiveData(RecipeUiState())
    val recipeUiState: LiveData<RecipeUiState>
        get() = _recipeUiState

    fun loadRecipe(recipeId: Int) {
        viewModelScope.launch {
            val recipe = recipesRepository.getRecipeByIdFromCache(recipeId)
            val imageUrl = IMAGE_URL + recipe.imageUrl

            _recipeUiState.value = recipe.isFavoriteRecipe.let {
                recipeUiState.value?.copy(
                    recipe = recipe,
                    isFavorite = it,
                    recipeImage = imageUrl
                )
            }
        }
    }

    fun onFavoritesClicked() {
        val recipe = _recipeUiState.value?.recipe ?: return
        val isFavorite = !recipe.isFavoriteRecipe

        viewModelScope.launch {
            recipesRepository.updateFavoriteStatus(recipe.id, isFavorite)
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