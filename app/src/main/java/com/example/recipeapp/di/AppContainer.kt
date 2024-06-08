package com.example.recipeapp.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.recipeapp.BASE_URL
import com.example.recipeapp.RecipeApiService
import com.example.recipeapp.RecipesRepository
import com.example.recipeapp.model.AppDatabase
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

class AppContainer(context: Context) {
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

    private val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "recipes-database"
    )
        .fallbackToDestructiveMigration()
        .build()

    private val categoriesDao = db.categoriesDao()
    private val recipesDao = db.recipesDao()

    private val repository = RecipesRepository(
        recipesDao = recipesDao,
        categoriesDao = categoriesDao,
        recipesApiService = service,
        ioDispatcher = Dispatchers.IO
    )

    val categoriesListViewModelFactory = CategoriesListViewModelFactory(repository)
}