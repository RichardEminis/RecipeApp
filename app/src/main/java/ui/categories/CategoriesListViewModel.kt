package ui.categories

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.RecipesRepository
import kotlinx.coroutines.launch
import model.Category

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
            _categoriesList.value = categoriesList.value?.copy(categories = repository.getCategoriesFromCache())

            val categories = repository.getCategories()
            _categoriesList.value = categoriesList.value?.copy(categories = categories)

            repository.saveCategoriesToCache(categories)
        }
    }

    fun getCategoryById(categoryId: Int): Category? {
        return _categoriesList.value?.categories?.find { it.id == categoryId }
    }
}