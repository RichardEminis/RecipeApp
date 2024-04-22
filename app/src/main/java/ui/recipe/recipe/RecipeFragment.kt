package ui.recipe.recipe

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeapp.ARG_RECIPE
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentRecipeBinding
import data.STUB
import model.Recipe
import ui.recipe.RecipeViewModel

class RecipeFragment : Fragment() {

    private val binding: FragmentRecipeBinding by lazy {
        FragmentRecipeBinding.inflate(layoutInflater)
    }

    private val viewModel: RecipeViewModel by viewModels()

    private var recipeId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recipe = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(ARG_RECIPE, Recipe::class.java)
        } else {
            arguments?.getParcelable(ARG_RECIPE)
        }

        recipe?.let {
            recipeId = it.id
            binding.recipeText.text = it.title
        }

        viewModel.loadRecipe(recipeId, requireContext())

        initUI()

        initRecycler()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initUI() {
        viewModel.recipeUiState.observe(viewLifecycleOwner) { state ->
            state?.let { item ->
                Log.i("!!!", "Is favorite: ${item.isFavorite}")

                if (item.isFavorite) {
                    binding.ibFavorite.setImageResource(R.drawable.ic_heart)
                } else {
                    binding.ibFavorite.setImageResource(R.drawable.ic_heart_empty)
                }
            }
        }

        val btnFavorite = binding.ibFavorite

        btnFavorite.setOnClickListener {
            viewModel.onFavoritesClicked()
        }
    }

    private fun initRecycler() {
        val ingredientsAdapter = IngredientsAdapter(STUB.getRecipeById(recipeId))
        val methodAdapter = MethodAdapter(STUB.getRecipeById(recipeId))

        val linearLayoutManagerIngredients = LinearLayoutManager(context)
        binding.rvIngredients.layoutManager = linearLayoutManagerIngredients
        binding.rvIngredients.adapter = ingredientsAdapter

        val linearLayoutManagerMethod = LinearLayoutManager(context)
        binding.rvMethod.layoutManager = linearLayoutManagerMethod
        binding.rvMethod.adapter = methodAdapter

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(
                seekBar: SeekBar,
                progress: Int,
                fromUser: Boolean
            ) {
                binding.portionsValue.text = progress.toString()
                ingredientsAdapter.updateIngredients(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }
}