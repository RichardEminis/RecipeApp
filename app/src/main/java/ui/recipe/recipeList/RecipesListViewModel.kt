package ui.recipe.recipeList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipeapp.RecipesRepository
import model.Recipe

data class RecipesUiState(
    val recipes: List<Recipe>?
)

class RecipesListViewModel : ViewModel() {

    private val _recipesUiState = MutableLiveData<RecipesUiState?>()
    val recipesUiState: MutableLiveData<RecipesUiState?>
        get() = _recipesUiState

    private val repository = RecipesRepository()

    fun loadRecipesByCategoryId(categoryId: Int) {
        repository.getRecipes(categoryId) { recipes ->
            _recipesUiState.postValue(RecipesUiState(recipes))
        }
    }
}