package com.example.recipeapp.ui.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.RecipesRepository
import com.example.recipeapp.model.Category
import kotlinx.coroutines.launch

data class CategoriesListState(
    val categories: List<Category> = emptyList()
)

class CategoriesListViewModel(private val recipesRepository: RecipesRepository) : ViewModel() {
    private val _categoriesList = MutableLiveData(CategoriesListState())
    val categoriesList: LiveData<CategoriesListState>
        get() = _categoriesList

    fun loadCategories() {
        viewModelScope.launch {
            val cachedCategories = recipesRepository.getCategoriesFromCache()
            _categoriesList.value = _categoriesList.value?.copy(categories = cachedCategories)

            val categories = recipesRepository.getCategories()
            _categoriesList.value = _categoriesList.value?.copy(categories = categories)

            recipesRepository.saveCategoriesToCache(categories)
        }
    }

    fun getCategoryById(categoryId: Int): Category? {
        return _categoriesList.value?.categories?.find { it.id == categoryId }
    }
}