package ui.recipe.recipeList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.RecipesRepository
import kotlinx.coroutines.launch
import model.Recipe

data class RecipesUiState(
    val recipes: List<Recipe> = emptyList()
)

class RecipesListViewModel : ViewModel() {

    private val _recipesUiState = MutableLiveData(RecipesUiState())
    val recipesUiState: MutableLiveData<RecipesUiState>
        get() = _recipesUiState

    private val repository = RecipesRepository()

    fun loadRecipesByCategoryId(categoryId: Int) {
        viewModelScope.launch {
            val recipes = repository.getRecipes(categoryId)
            _recipesUiState.value = _recipesUiState.value?.copy(recipes = recipes)
        }
    }
}