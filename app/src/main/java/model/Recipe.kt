package model

data class Recipe(
    val id: Int,
    val title: String,
    val listOfIngredients: List<Ingredient>,
    val method: String,
    val imageUrl: String
)
