package ui.recipe

import androidx.lifecycle.ViewModel
import model.Ingredient

data class RecipeUiState(
    var id:  Int? = null,
    var title:  String? = null,
    var listOfIngredients:  List<Ingredient>? = null,
    var method :  List<String>? = null,
    var imageUrl :  String? = null,
)

class RecipeViewModel : ViewModel()