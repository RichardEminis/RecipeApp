package com.example.recipeapp

import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipeApiService {
    @GET("category")
    fun getCategories(): Call<List<Category>>

    @GET("category/{categoryId}/recipes")
    fun getRecipes(@Path("categoryId") categoryId: Int): Call<List<Recipe>>
}