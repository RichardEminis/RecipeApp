package ui.recipe.recipeList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import data.STUB
import model.Recipe

class RecipesListViewModel : ViewModel() {

    private val _recipesUiState = MutableLiveData<RecipesUiState?>()
    val recipesUiState: MutableLiveData<RecipesUiState?>
        get() = _recipesUiState

    fun loadRecipesByCategoryId(categoryId: Int) {
        val recipes = STUB.getRecipesByCategoryId(categoryId)
        val uiState = recipes?.let { RecipesUiState(it) }
        _recipesUiState.value = uiState
    }

    data class RecipesUiState(
        val recipes: List<Recipe>
    )
}