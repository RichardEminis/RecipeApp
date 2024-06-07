package com.example.recipeapp.ui.categories

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.recipeapp.BASE_URL
import com.example.recipeapp.RecipeApiService
import com.example.recipeapp.RecipesRepository
import com.example.recipeapp.databinding.FragmentListCategoriesBinding
import com.example.recipeapp.model.AppDatabase
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import okhttp3.Dispatcher
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

class CategoriesListFragment : Fragment() {

    private var recyclerView: RecyclerView? = null
    private var categoriesAdapter: CategoriesListAdapter = CategoriesListAdapter()
    private lateinit var viewModel: CategoriesListViewModel

    private var _binding: FragmentListCategoriesBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for ActivityMainBinding must not be null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

         val logInterceptor = HttpLoggingInterceptor { message ->
            Log.d("OkHttp", message)
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(logInterceptor)
            .build()
        val contentType = "application/json".toMediaType()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
       val service: RecipeApiService = retrofit.create(RecipeApiService::class.java)

        val db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java, "recipes-database"
        )
            .fallbackToDestructiveMigration()
            .build()

        val categoriesDao = db.categoriesDao()
        val recipesDao = db.recipesDao()

        val repository = RecipesRepository(
            recipesDao = recipesDao,
            categoriesDao = categoriesDao,
            recipesApiService = service,
            ioDispatcher = Dispatchers.IO
        )

        viewModel = CategoriesListViewModel(repository)
    }

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