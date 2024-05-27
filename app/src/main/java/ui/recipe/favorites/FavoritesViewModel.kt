package ui.recipe.favorites

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipeapp.FAVORITES_SHARED_PREFERENCES
import com.example.recipeapp.KEY_FAVORITES
import com.example.recipeapp.RecipesRepository
import model.Recipe

data class FavoritesUiState(
    val favoriteRecipes: List<Recipe> = emptyList()
)

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    private val _favoritesUiState = MutableLiveData(FavoritesUiState())
    val favoritesUiState: LiveData<FavoritesUiState>
        get() = _favoritesUiState

    private val repository = RecipesRepository()

    fun loadFavorites() {
        val favoritesSet = getFavorites() ?: emptySet()
        val favoriteIds = favoritesSet.map { it.toInt() }
        repository.getRecipes(favoriteIds) { recipes ->
            _favoritesUiState.postValue(
                FavoritesUiState(favoriteRecipes = recipes)
            )
        }
    }

    private fun getFavorites(): Set<String>? {
        val sharedPrefs = getApplication<Application>().getSharedPreferences(
            FAVORITES_SHARED_PREFERENCES, Context.MODE_PRIVATE
        )
        return sharedPrefs.getStringSet(KEY_FAVORITES, emptySet())
    }
}