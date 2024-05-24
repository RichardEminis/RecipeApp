package ui.recipe.recipeList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipeapp.RecipesRepository
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
        val repository = RecipesRepository()
        val recipes = repository.getRecipes(categoryId)
        _recipesUiState.value = recipesUiState.value?.copy(recipes = recipes)
    }
}