package ui.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import data.STUB
import model.Category

class CategoriesListViewModel : ViewModel() {

    private val _categoriesList = MutableLiveData<List<Category>>()
    val categoriesList: LiveData<List<Category>>
        get() = _categoriesList

    init {
        loadCategories()
    }

    private fun loadCategories() {
        _categoriesList.value = STUB.getCategories()
    }
}