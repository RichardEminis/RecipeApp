package model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Recipe(
    val id: Int,
    val title: String,
    val listOfIngredients: @RawValue List<Ingredient>,
    val method: List<String>,
    val imageUrl: String,
): Parcelable