package ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.ARG_CATEGORY_ID
import com.example.recipeapp.ARG_CATEGORY_IMAGE_URL
import com.example.recipeapp.ARG_CATEGORY_NAME
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentListCategoriesBinding
import ui.recipe.recipeList.RecipesListFragment

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
        viewModel.getCategoryById(categoryId)?.let { category ->
            val bundle = bundleOf(
                ARG_CATEGORY_ID to categoryId,
                ARG_CATEGORY_NAME to category.title,
                ARG_CATEGORY_IMAGE_URL to category.imageUrl
            )
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                add<RecipesListFragment>(R.id.mainContainer, args = bundle)
            }
        }
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