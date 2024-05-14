package ui.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import data.STUB
import model.Category
import ui.recipe.RecipeUiState

data class CategoriesListState(
    val categories: List<Category> = emptyList()
)

class CategoriesListViewModel : ViewModel() {
    private val _categoriesList = MutableLiveData<CategoriesListState>()
    val categoriesList: LiveData<CategoriesListState>
        get() = _categoriesList

    fun loadCategories() {
        val categories = STUB.getCategories()
        _categoriesList.value = CategoriesListState(categories = categories)
    }
}