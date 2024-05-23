package com.example.recipeapp

import model.Category
import retrofit2.Call
import retrofit2.http.GET

interface RecipeApiService {
    @GET("category")
    fun getCategories(): Call<List<Category>>
}