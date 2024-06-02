package com.example.recipeapp.ui.categories

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.RecipesRepository
import kotlinx.coroutines.launch
import com.example.recipeapp.model.Category

data class CategoriesListState(
    val categories: List<Category> = emptyList()
)

class CategoriesListViewModel(application: Application) : AndroidViewModel(application) {
    private val _categoriesList = MutableLiveData(CategoriesListState())
    val categoriesList: LiveData<CategoriesListState>
        get() = _categoriesList

    private val repository = RecipesRepository(application)

    fun loadCategories() {
        viewModelScope.launch {
            val cachedCategories = repository.getCategoriesFromCache()
            _categoriesList.value = _categoriesList.value?.copy(categories = cachedCategories)

            val categories = repository.getCategories()
            _categoriesList.value = _categoriesList.value?.copy(categories = categories)

            repository.saveCategoriesToCache(categories)
        }
    }

    fun getCategoryById(categoryId: Int): Category? {
        return _categoriesList.value?.categories?.find { it.id == categoryId }
    }
}