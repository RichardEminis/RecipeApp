package com.example.recipeapp.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.databinding.FragmentListCategoriesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesListFragment : Fragment() {

    private var recyclerView: RecyclerView? = null
    private var categoriesAdapter: CategoriesListAdapter = CategoriesListAdapter()
    private val viewModel: CategoriesListViewModel by viewModels()

    private var _binding: FragmentListCategoriesBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for ActivityMainBinding must not be null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        _binding = FragmentListCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()

        viewModel.loadCategories()

        viewModel.categoriesList.observe(viewLifecycleOwner) { categoriesListState ->
            categoriesAdapter.dataSet = categoriesListState.categories
        }
    }

    private fun openRecipesByCategoryId(categoryId: Int) {
        val action = CategoriesListFragmentDirections
            .actionCategoriesListFragmentToRecipesListFragment(
                viewModel.getCategoryById(categoryId)
                    ?: throw IllegalArgumentException("Category with id $categoryId not found")
            )
        findNavController().navigate(action)
    }

    private fun initRecycler() {
        recyclerView = binding.rvCategories
        recyclerView?.adapter = categoriesAdapter

        categoriesAdapter.setOnItemClickListener(object :
            CategoriesListAdapter.OnItemClickListener {
            override fun onItemClick(categoryId: Int) {
                openRecipesByCategoryId(categoryId)
            }
        })
    }
}