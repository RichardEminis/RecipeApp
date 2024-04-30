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
import data.STUB
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

    fun loadRecipe(recipeId: Int, context: Context) {
        val favorites = getFavorites()
        val currentState = _recipeUiState.value

        val drawableRecipeImage: Drawable? = try {
            val inputStream: InputStream? =
                context.assets?.open(STUB.getRecipeById(recipeId).imageUrl)
            Drawable.createFromStream(inputStream, null)
        } catch (exception: Exception) {
            Log.e("mylog", "Error: $exception")
            null
        }

        val newState = currentState?.copy(
            recipe = STUB.getRecipeById(recipeId),
            isFavorite = favorites?.contains(recipeId.toString()) ?: false,
            recipeImage = drawableRecipeImage
        ) ?: RecipeUiState(
            recipe = STUB.getRecipeById(recipeId),
            isFavorite = favorites?.contains(recipeId.toString()) ?: false,
            recipeImage = drawableRecipeImage
        )

        _recipeUiState.value = newState

        //TODO: load from network
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
            val updatedState = currentState.copy(portionsCount = portionsCount)
            _recipeUiState.value = updatedState
        }
    }
}