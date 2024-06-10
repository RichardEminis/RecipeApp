package com.example.recipeapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.TypeConverter
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
@Parcelize
@Entity(tableName = "recipe")
data class Recipe(
    @PrimaryKey val id: Int,
    val title: String,
    val ingredients: List<Ingredient>,
    val method: List<String>,
    val imageUrl: String,
    val isFavoriteRecipe: Boolean = false,
    @ColumnInfo(name = "category_id")
    @Transient val categoryId: Int? = null
) : Parcelable

class Converters {
    @TypeConverter
    fun fromIngredientsList(value: List<Ingredient>): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun toIngredientsList(value: String): List<Ingredient> {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun fromMethodList(value: List<String>): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun toMethodList(value: String): List<String> {
        return Json.decodeFromString(value)
    }
}

@Dao
interface RecipesDao {
    @Query("SELECT * FROM recipe WHERE id = :recipeId")
    fun getRecipeById(recipeId: Int): Recipe

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipes(recipes: List<Recipe>)

    @Query("SELECT * FROM recipe WHERE isFavoriteRecipe = 1")
    fun getFavoriteRecipes(): List<Recipe>

    @Query("UPDATE recipe SET isFavoriteRecipe = :isFavorite WHERE id = :recipeId")
    fun updateFavoriteStatus(recipeId: Int, isFavorite: Boolean)

    @Query("SELECT * FROM recipe WHERE category_id = :categoryId")
    fun getRecipesByCategoryId(categoryId: Int): List<Recipe>
}