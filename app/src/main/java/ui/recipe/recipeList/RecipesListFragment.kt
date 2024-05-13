package ui.recipe.recipeList

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
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
import com.example.recipeapp.ARG_RECIPE_ID
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentRecipesListBinding
import data.STUB
import model.Recipe
import ui.recipe.recipe.RecipeFragment
import java.io.InputStream

class RecipesListFragment : Fragment() {

    private var recyclerView: RecyclerView? = null

    private val viewModel: RecipesListViewModel by viewModels()

    private var _binding: FragmentRecipesListBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for ActivityMainBinding must not be null")

    private var categoryName: String? = null
    private var categoryImageUrl: String? = null
    private var categoryId: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryId = requireArguments().getInt(ARG_CATEGORY_ID)
        categoryName = requireArguments().getString(ARG_CATEGORY_NAME)
        categoryImageUrl = requireArguments().getString(ARG_CATEGORY_IMAGE_URL)

        binding.recipeListText.text = categoryName

        val drawable = categoryImageUrl?.let { getDrawableFromAssets(it, requireContext()) }
        binding.recipeListImage.setImageDrawable(drawable)

        viewModel.loadRecipesByCategoryId(categoryId ?: 0)

        viewModel.recipesUiState.observe(viewLifecycleOwner) { uiState ->
            if (uiState != null) {
                updateRecipesList(uiState.recipes)
            }
        }

        initRecycler()
    }

    private fun openRecipeByRecipeId(recipeId: Int) {

        val bundle = bundleOf(ARG_RECIPE_ID to recipeId)

        parentFragmentManager.commit {
            setReorderingAllowed(true)
            add<RecipeFragment>(R.id.mainContainer, args = bundle)
        }
    }

    private fun initRecycler() {
        val adapter = STUB.getRecipesByCategoryId(categoryId)?.let { RecipeListAdapter(it) }
        recyclerView = binding.rvRecipes
        recyclerView?.adapter = adapter

        adapter?.setOnItemClickListener(object : RecipeListAdapter.OnItemClickListener {
            override fun onItemClick(recipeId: Int) {
                openRecipeByRecipeId(recipeId)
            }
        })
    }

    private fun getDrawableFromAssets(
        imageUrl: String,
        context: Context
    ): Drawable? {
        return try {
            val inputStream: InputStream? = context.assets?.open(imageUrl)
            Drawable.createFromStream(inputStream, null)
        } catch (exception: Exception) {
            Log.e("mylog", "Error: $exception")
            null
        }
    }

    private fun updateRecipesList(recipes: List<Recipe>) {
        val recipeListAdapter = RecipeListAdapter(recipes)
        recyclerView?.adapter = recipeListAdapter

        recipeListAdapter.setOnItemClickListener(object : RecipeListAdapter.OnItemClickListener {
            override fun onItemClick(recipeId: Int) {
                openRecipeByRecipeId(recipeId)
            }
        })
    }
}