package ui.recipe.recipeList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import data.STUB
import model.Recipe

data class RecipesUiState(
    val recipes: List<Recipe>?
)

class RecipesListViewModel : ViewModel() {

    private val _recipesUiState = MutableLiveData<RecipesUiState?>()
    val recipesUiState: MutableLiveData<RecipesUiState?>
        get() = _recipesUiState

    fun loadRecipesByCategoryId(categoryId: Int) {
        val recipes = STUB.getRecipesByCategoryId(categoryId)
        _recipesUiState.value = recipesUiState.value?.copy(recipes = recipes)
    }
}