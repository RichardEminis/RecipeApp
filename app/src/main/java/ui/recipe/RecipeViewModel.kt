package ui.recipe

import androidx.lifecycle.ViewModel
import model.Recipe

class RecipeViewModel : ViewModel()

data class RecipeUiState(
    var recipe: Recipe? = null,
    var portionsCount: Int = 1,
    var inFavorites: Boolean = false,
)