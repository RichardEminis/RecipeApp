package com.example.recipeapp

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import model.Category
import model.Recipe
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Call
import retrofit2.Retrofit
import android.os.Handler
import android.os.Looper
import retrofit2.Response
import java.util.concurrent.Executors

class RecipesRepository {
    private val threadPool = Executors.newFixedThreadPool(10)
    private val resultHandler = Handler(Looper.getMainLooper())
    private val contentType = "application/json".toMediaType()
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://recipes.androidsprint.ru/api/")
        .addConverterFactory(Json.asConverterFactory(contentType))
        .build()
    private val service: RecipeApiService = retrofit.create(RecipeApiService::class.java)

    fun getCategories(callback: (List<Category>) -> Unit) {
        threadPool.execute {
            val categoriesCall: Call<List<Category>> = service.getCategories()
            val categoriesResponse: Response<List<Category>> = categoriesCall.execute()
            resultHandler.post {
                callback(categoriesResponse.body() ?: emptyList())
            }
        }
    }

    fun getRecipes(categoryId: Int, callback: (List<Recipe>) -> Unit) {
        threadPool.execute {
            val recipesCall: Call<List<Recipe>> = service.getRecipes(categoryId)
            val recipesResponse: Response<List<Recipe>> = recipesCall.execute()
            resultHandler.post {
                callback(recipesResponse.body() ?: emptyList())
            }
        }
    }

    fun getRecipeById(recipeId: Int, callback: (Recipe?) -> Unit) {
        threadPool.execute {
            val recipeCall: Call<Recipe> = service.getRecipeById(recipeId)
            val recipeResponse: Response<Recipe> = recipeCall.execute()
            resultHandler.post {
                callback(recipeResponse.body())
            }
        }
    }
}