package com.example.recipeapp

import com.example.recipeapp.model.CategoriesDao
import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.model.RecipesDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class RecipesRepository @Inject constructor(
    private val recipesDao: RecipesDao,
    private val categoriesDao: CategoriesDao,
    private val recipesApiService: RecipeApiService,
) {

    private val ioDispatcher: CoroutineContext = Dispatchers.IO

    suspend fun getCategoriesFromCache(): List<Category> {
        return withContext(ioDispatcher) {
            categoriesDao.getAllCategories()
        }
    }

    suspend fun saveCategoriesToCache(categories: List<Category>) {
        return withContext(ioDispatcher) {
            categoriesDao.insertCategories(categories)
        }
    }

    suspend fun getFavoriteRecipes(): List<Recipe> {
        return withContext(ioDispatcher) {
            recipesDao.getFavoriteRecipes()
        }
    }

    suspend fun updateFavoriteStatus(recipeId: Int, isFavorite: Boolean) {
        withContext(ioDispatcher) {
            recipesDao.updateFavoriteStatus(recipeId, isFavorite)
        }
    }

    suspend fun getRecipeByIdFromCache(recipeId: Int): Recipe {
        return withContext(ioDispatcher) {
            recipesDao.getRecipeById(recipeId)
        }
    }

    suspend fun getRecipesWithFavorites(categoryId: Int): List<Recipe> {
        return withContext(ioDispatcher) {
            val recipesCall: Call<List<Recipe>> = recipesApiService.getRecipes(categoryId)
            val recipesResponse: Response<List<Recipe>> = recipesCall.execute()
            val recipes = recipesResponse.body() ?: emptyList()

            val favoriteRecipes = recipesDao.getFavoriteRecipes()
            val favoriteIds = favoriteRecipes.map { it.id }.toSet()
            val updatedRecipes = recipes.map { recipe ->
                val isFavorite = favoriteIds.contains(recipe.id)
                recipe.copy(isFavoriteRecipe = isFavorite)
            }

            recipesDao.insertRecipes(updatedRecipes)

            updatedRecipes
        }
    }

    suspend fun getCategories(): List<Category> {
        return withContext(Dispatchers.IO) {
            val categoriesCall: Call<List<Category>> = recipesApiService.getCategories()
            val categoriesResponse: Response<List<Category>> = categoriesCall.execute()
            categoriesResponse.body() ?: emptyList()
        }
    }
}