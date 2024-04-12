package ui.recipe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import data.STUB
import model.Recipe

data class RecipeUiState(
    var recipe: Recipe? = null,
    var portionsCount: Int = 1,
    var isFavorite: Boolean = false,
)

class RecipeViewModel : ViewModel() {
    private val _recipeUiState = MutableLiveData<RecipeUiState>()
    val recipeUiState: LiveData<RecipeUiState>
        get() = _recipeUiState

    init {
        Log.i("!!!", "Initializing RecipeViewModel")
        _recipeUiState.value = RecipeUiState(
            isFavorite = true,
            portionsCount = 1
        )
    }

    fun loadRecipe(recipeId: Int) {

        //TODO: load from network

        _recipeUiState.value = RecipeUiState(recipe = STUB.getRecipeById(recipeId))
    }
}