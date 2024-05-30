package ui.categories

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

class CategoriesListViewModel : ViewModel() {
    private val _categoriesList = MutableLiveData(CategoriesListState())
    val categoriesList: LiveData<CategoriesListState>
        get() = _categoriesList

    private val repository = RecipesRepository()

    fun loadCategories() {
        viewModelScope.launch {
            val categories = repository.getCategories()
            _categoriesList.value = categoriesList.value?.copy(categories = categories)
        }
    }

    fun getCategoryById(categoryId: Int): Category? {
        return _categoriesList.value?.categories?.find { it.id == categoryId }
    }
}