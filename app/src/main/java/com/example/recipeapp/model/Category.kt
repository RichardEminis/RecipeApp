package com.example.recipeapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
@Entity(tableName = "category")
data class Category(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "1") val title: String,
    @ColumnInfo(name = "2") val description: String,
    @ColumnInfo(name = "3") val imageUrl: String,
): Parcelable

@Dao
interface CategoriesDao {
    @Query("SELECT * FROM category")
    fun getAllCategories(): List<Category>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategories(categories: List<Category>)
}