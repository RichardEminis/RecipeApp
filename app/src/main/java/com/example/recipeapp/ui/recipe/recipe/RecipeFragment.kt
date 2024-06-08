package com.example.recipeapp.ui.recipe.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentRecipeBinding
import com.example.recipeapp.ui.recipe.RecipeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeFragment : Fragment() {
    private val binding: FragmentRecipeBinding by lazy {
        FragmentRecipeBinding.inflate(layoutInflater)
    }

    private lateinit var viewModel: RecipeViewModel
    private val args: RecipeFragmentArgs by navArgs()

    private val ingredientsAdapter = IngredientsAdapter()
    private val methodAdapter = MethodAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recipeId: Int = args.recipeId

        viewModel.loadRecipe(recipeId)
        initUI()
    }

    private fun initUI() {
        viewModel.recipeUiState.observe(viewLifecycleOwner) { state ->
            state?.let { item ->
                binding.ibFavorite.setImageResource(
                    if (item.isFavorite) R.drawable.ic_heart else R.drawable.ic_heart_empty
                )

                item.recipeImage?.let { imageUrl ->
                    Glide.with(this)
                        .load(imageUrl)
                        .placeholder(R.drawable.img_placeholder)
                        .error(R.drawable.img_error)
                        .into(binding.recipeImage)
                }

                binding.recipeText.text = item.recipe?.title

                ingredientsAdapter.ingredientsUpdateDataSet(
                    item.recipe?.ingredients ?: emptyList(), item.portionsCount
                )
                methodAdapter.methodUpdateDataSet(item.recipe?.method ?: emptyList())

                binding.rvIngredients.layoutManager = LinearLayoutManager(context)
                binding.rvIngredients.adapter = ingredientsAdapter

                binding.rvMethod.layoutManager = LinearLayoutManager(context)
                binding.rvMethod.adapter = methodAdapter

                binding.seekBar.setOnSeekBarChangeListener(PortionSeekBarListener { progress ->
                    binding.portionsValue.text = progress.toString()
                    viewModel.updatePortionsCount(progress)
                    ingredientsAdapter.ingredientsUpdateDataSet(
                        item.recipe?.ingredients ?: emptyList(), progress
                    )
                })
            }
        }

        binding.ibFavorite.setOnClickListener {
            viewModel.onFavoritesClicked()
        }
    }

    class PortionSeekBarListener(private val onChangeIngredients: (Int) -> Unit) :
        SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            onChangeIngredients(progress)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {}
        override fun onStopTrackingTouch(seekBar: SeekBar) {}
    }
}