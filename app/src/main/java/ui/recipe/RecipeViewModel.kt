package ui.recipe

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.FAVORITES_SHARED_PREFERENCES
import com.example.recipeapp.IMAGE_URL
import com.example.recipeapp.KEY_FAVORITES
import com.example.recipeapp.RecipesRepository
import kotlinx.coroutines.launch
import model.Recipe

data class RecipeUiState(
    var recipe: Recipe? = null,
    var portionsCount: Int = 1,
    var isFavorite: Boolean = false,
    var recipeImage: String? = null,
)

class RecipeViewModel(application: Application) : AndroidViewModel(application) {
    private val _recipeUiState = MutableLiveData<RecipeUiState>()
    val recipeUiState: LiveData<RecipeUiState>
        get() = _recipeUiState
    private val sharedPreferences =
        application.getSharedPreferences(FAVORITES_SHARED_PREFERENCES, Context.MODE_PRIVATE)
    private val repository = RecipesRepository()

    fun loadRecipe(recipeId: Int) {
        val favorites = getFavorites()
        val currentState = _recipeUiState.value

        viewModelScope.launch {
            val recipe = repository.getRecipeById(recipeId)
            val imageUrl = IMAGE_URL + recipe?.imageUrl

            val newState = currentState?.copy(
                recipe = recipe,
                isFavorite = favorites?.contains(recipeId.toString()) ?: false,
                recipeImage = imageUrl
            ) ?: RecipeUiState(
                recipe = recipe,
                isFavorite = favorites?.contains(recipeId.toString()) ?: false,
                recipeImage = imageUrl
            )

            _recipeUiState.postValue(newState)
        }
    }

    private fun getFavorites(): MutableSet<String>? {
        return sharedPreferences.getStringSet(KEY_FAVORITES, HashSet())?.toMutableSet()
    }

    fun onFavoritesClicked() {
        val recipeId = _recipeUiState.value?.recipe?.id.toString()
        val favorites = getFavorites()

        if (favorites != null) {
            if (favorites.contains(recipeId)) {
                favorites.remove(recipeId)
                _recipeUiState.value = _recipeUiState.value?.copy(isFavorite = false)
            } else {
                favorites.add(recipeId)
                _recipeUiState.value = _recipeUiState.value?.copy(isFavorite = true)
            }
        }

        favorites?.let { saveFavorites(it) }
    }

    private fun saveFavorites(favoritesSet: Set<String>) {
        val editor = sharedPreferences.edit()
        editor.putStringSet(KEY_FAVORITES, favoritesSet)
        editor.apply()
    }

    fun updatePortionsCount(portionsCount: Int) {
        _recipeUiState.value?.let { currentState ->
            _recipeUiState.value = currentState.copy(portionsCount = portionsCount)
        }
    }
}