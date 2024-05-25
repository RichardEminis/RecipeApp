package ui.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipeapp.RecipesRepository
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
        repository.getCategories { categories ->
            _categoriesList.value = categoriesList.value?.copy(categories = categories)
        }
    }

    fun getCategoryById(categoryId: Int): Category? {
        return _categoriesList.value?.categories?.find { it.id == categoryId }
    }
}