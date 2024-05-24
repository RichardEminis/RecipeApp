package com.example.recipeapp

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import model.Category
import model.Recipe
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Call
import retrofit2.Retrofit
import java.util.concurrent.Executors

class RecipesRepository {
    private val threadPool = Executors.newFixedThreadPool(10)
    private val contentType = "application/json".toMediaType()
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://recipes.androidsprint.ru/api/")
        .addConverterFactory(Json.asConverterFactory(contentType))
        .build()
    private val service: RecipeApiService = retrofit.create(RecipeApiService::class.java)

    fun getCategories(): List<Category> {
        val categoriesCall: Call<List<Category>> = service.getCategories()
        val categoriesResponse: retrofit2.Response<List<Category>> = categoriesCall.execute()

        return categoriesResponse.body() ?: emptyList()
    }

    fun getRecipes(categoryId: Int): List<Recipe> {
        val recipesCall: Call<List<Recipe>> = service.getRecipes(categoryId)
        val recipesResponse: retrofit2.Response<List<Recipe>> = recipesCall.execute()

        return recipesResponse.body() ?: emptyList()
    }
}