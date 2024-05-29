package com.example.recipeapp

import android.util.Log
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import model.Category
import model.Recipe
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit

class RecipesRepository {
    private val logInterceptor = HttpLoggingInterceptor { message ->
        Log.d("OkHttp", message)
    }.apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(logInterceptor)
        .build()
    private val contentType = "application/json".toMediaType()
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(Json.asConverterFactory(contentType))
        .build()
    private val service: RecipeApiService = retrofit.create(RecipeApiService::class.java)

    suspend fun getCategories(): List<Category> {
        return withContext(Dispatchers.IO) {
            val categoriesCall: Call<List<Category>> = service.getCategories()
            val categoriesResponse: Response<List<Category>> = categoriesCall.execute()
            categoriesResponse.body() ?: emptyList()
        }
    }

    suspend fun getRecipes(categoryId: Int): List<Recipe> {
        return withContext(Dispatchers.IO) {
            val recipesCall: Call<List<Recipe>> = service.getRecipes(categoryId)
            val recipesResponse: Response<List<Recipe>> = recipesCall.execute()
            recipesResponse.body() ?: emptyList()
        }
    }

    suspend fun getRecipeById(recipeId: Int): Recipe? {
        return withContext(Dispatchers.IO) {
            val recipeCall: Call<Recipe> = service.getRecipeById(recipeId)
            val recipeResponse: Response<Recipe> = recipeCall.execute()
            recipeResponse.body()
        }
    }

    suspend fun getRecipes(ids: List<Int>): List<Recipe> {
        return withContext(Dispatchers.IO) {
            val recipesCall: Call<List<Recipe>> = service.getRecipes(ids.joinToString(","))
            val recipesResponse: Response<List<Recipe>> = recipesCall.execute()
            recipesResponse.body() ?: emptyList()
        }
    }
}