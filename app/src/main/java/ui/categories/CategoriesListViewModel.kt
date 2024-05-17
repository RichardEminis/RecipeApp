package ui.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import data.STUB
import model.Category

data class CategoriesListState(
    val categories: List<Category> = emptyList()
)

class CategoriesListViewModel : ViewModel() {
    private val _categoriesList = MutableLiveData<CategoriesListState>()
    val categoriesList: LiveData<CategoriesListState>
        get() = _categoriesList

    fun loadCategories() {
        val categories = STUB.getCategories()
        val currentState = _categoriesList.value
        val newState = currentState?.copy(categories = categories)
            ?: CategoriesListState(categories = categories)
        _categoriesList.value = newState
    }
}