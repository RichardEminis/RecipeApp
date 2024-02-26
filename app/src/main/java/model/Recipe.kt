package model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(
    val id: Int,
    val title: String,
    val listOfIngredients: List<Ingredient>,
    val method: List<String>,
    val imageUrl: String,
): Parcelable