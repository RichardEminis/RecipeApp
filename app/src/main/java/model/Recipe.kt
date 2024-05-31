package model

import android.os.Parcelable
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
@Entity(tableName = "recipe")
data class Recipe(
    @PrimaryKey val id: Int,
    val title: String,
    val ingredients: List<Ingredient>,
    val method: List<String>,
    val imageUrl: String,
) : Parcelable

@Dao
interface RecipesDao {
    @Query("SELECT * FROM recipe")
    suspend fun getRecipesByCategory(categoryId: Int): List<Recipe>

    @Insert
    suspend fun insertRecipes(recipes: List<Recipe>)
}