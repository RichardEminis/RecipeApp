package ui.recipe

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import data.STUB
import model.Recipe

data class RecipeUiState(
    var recipe: Recipe? = null,
    var portionsCount: Int = 1,
    var isFavorite: Boolean = false,
)

class RecipeViewModel(application: Application) : AndroidViewModel(application) {
    private val _recipeUiState = MutableLiveData<RecipeUiState>()
    val recipeUiState: LiveData<RecipeUiState>
        get() = _recipeUiState
    private val sharedPreferences =
        application.getSharedPreferences("FavoritesSharedPreferences", Context.MODE_PRIVATE)

    init {
        Log.i("!!!", "Initializing RecipeViewModel")
        _recipeUiState.value = RecipeUiState(
            isFavorite = false,
            portionsCount = 1
        )
    }

    fun loadRecipe(recipeId: Int) {
        val favorites = getFavorites()
        val currentState = _recipeUiState.value

        val newState = currentState?.let {
            RecipeUiState(
                recipe = STUB.getRecipeById(recipeId),
                portionsCount = it.portionsCount,
                isFavorite = favorites?.contains(recipeId.toString()) ?: false
            )
        }

        //TODO: load from network

        _recipeUiState.value = RecipeUiState(recipe = STUB.getRecipeById(recipeId))
    }

    private fun getFavorites(): MutableSet<String>? {
        return sharedPreferences.getStringSet("favoriteRecipes", HashSet())?.toMutableSet()
    }

    fun onFavoritesClicked(recipeId: Int) {
        val currentState = _recipeUiState.value ?: return
        val favorites = getFavorites()


        val newIsFavorite = if (currentState.isFavorite) {
            favorites?.remove(recipeId.toString())
            false
        } else {
            favorites?.add(recipeId.toString())
            true
        }

        val newState = currentState.copy(isFavorite = newIsFavorite)

        _recipeUiState.value = newState

        favorites?.let { saveFavorites(it) }
    }

    private fun saveFavorites(favoritesSet: Set<String>) {
        val editor = sharedPreferences.edit()
        editor.putStringSet("favoriteRecipes", favoritesSet)
        editor.apply()
    }
}