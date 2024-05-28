package ui.recipe.recipeList

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.databinding.FragmentRecipesListBinding
import java.io.InputStream

class RecipesListFragment : Fragment() {

    private var recyclerView: RecyclerView? = null
    private val args: RecipesListFragmentArgs by navArgs()
    private val viewModel: RecipesListViewModel by viewModels()
    private var recipeListAdapter: RecipeListAdapter = RecipeListAdapter()

    private var _binding: FragmentRecipesListBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for ActivityMainBinding must not be null")

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

        val category = args.category
        binding.recipeListText.text = category.title

        val drawable = getDrawableFromAssets(category.imageUrl, requireContext())
        binding.recipeListImage.setImageDrawable(drawable)

        viewModel.loadRecipesByCategoryId(category.id)

        viewModel.recipesUiState.observe(viewLifecycleOwner) { uiState ->
            if (uiState != null) {
                recipeListAdapter.dataSet = uiState.recipes
            }
        }

        initRecycler()
    }

    private fun openRecipeByRecipeId(recipeId: Int) {
        findNavController().navigate(
            RecipesListFragmentDirections.actionRecipesListFragmentToRecipeFragment(
                recipeId
            )
        )
    }

    private fun initRecycler() {
        recyclerView = binding.rvRecipes
        recyclerView?.adapter = recipeListAdapter

        recipeListAdapter.setOnItemClickListener(object : RecipeListAdapter.OnItemClickListener {
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
}