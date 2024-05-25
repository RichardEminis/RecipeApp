package ui.recipe

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipeapp.FAVORITES_SHARED_PREFERENCES
import com.example.recipeapp.KEY_FAVORITES
import com.example.recipeapp.RecipesRepository
import model.Recipe
import java.io.InputStream

data class RecipeUiState(
    var recipe: Recipe? = null,
    var portionsCount: Int = 1,
    var isFavorite: Boolean = false,
    var recipeImage: Drawable? = null,
)

class RecipeViewModel(application: Application) : AndroidViewModel(application) {
    private val _recipeUiState = MutableLiveData<RecipeUiState>()
    val recipeUiState: LiveData<RecipeUiState>
        get() = _recipeUiState
    private val sharedPreferences =
        application.getSharedPreferences(FAVORITES_SHARED_PREFERENCES, Context.MODE_PRIVATE)
    private val repository = RecipesRepository()

    fun loadRecipe(recipeId: Int, context: Context) {
        val favorites = getFavorites()
        val currentState = _recipeUiState.value

        repository.getRecipeById(recipeId) { recipe ->
            val drawableRecipeImage: Drawable? = try {
                val inputStream: InputStream? = recipe?.imageUrl?.let { context.assets?.open(it) }
                Drawable.createFromStream(inputStream, null)
            } catch (exception: Exception) {
                Log.e("mylog", "Error: $exception")
                null
            }

            val newState = currentState?.copy(
                recipe = recipe,
                isFavorite = favorites?.contains(recipeId.toString()) ?: false,
                recipeImage = drawableRecipeImage
            ) ?: RecipeUiState(
                recipe = recipe,
                isFavorite = favorites?.contains(recipeId.toString()) ?: false,
                recipeImage = drawableRecipeImage
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