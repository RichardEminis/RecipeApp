package ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.recipeapp.R
import com.example.recipeapp.RecipeApiService
import com.example.recipeapp.databinding.ActivityMainBinding
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import model.Category
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import java.util.concurrent.Executors


class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for ActivityMainBinding must not be null")

    private val threadPool = Executors.newFixedThreadPool(10)
    private val logInterceptor = HttpLoggingInterceptor { message ->
        Log.d("OkHttp", message)
    }.apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(logInterceptor)
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("!!!", "Метод onCreate() выполняется на потоке: ${Thread.currentThread().name}")

        binding.favoritesButton.setOnClickListener {
            findNavController(R.id.nav_host_fragment).navigate(R.id.favoritesFragment)
        }

        binding.categoryButton.setOnClickListener {
            findNavController(R.id.nav_host_fragment).navigate(R.id.categoriesListFragment)
        }

        connect()
    }

    private fun connect() {
        val thread = Thread {
            Log.d("!!!", "Выполняю запрос на потоке: ${Thread.currentThread().name}")

            val request = Request.Builder()
                .url("https://recipes.androidsprint.ru/api/category")
                .build()

            client.newCall(request).execute().use { response ->
                Log.d("!!!", "responseCode ${response.code}")

                val json = response.body?.string() ?: ""
                Log.d("!!!", "Body $json")

                val categories = Json.decodeFromString<List<Category>>(json)
                Log.d("!!!", "Parsed categories: $categories")

                val categoryIds = categories.map { it.id }

                categoryIds.forEach { categoryId ->
                    threadPool.execute {
                        getRecipesFromUrl(categoryId)
                    }
                }
            }
        }
        thread.start()
    }

    private fun getRecipesFromUrl(categoryId: Int) {
        Log.d(
            "!!!",
            "Выполняю запрос рецептов для категории $categoryId на потоке: ${Thread.currentThread().name}"
        )

        val request = Request.Builder()
            .url("https://recipes.androidsprint.ru/api/category/$categoryId/recipes")
            .build()

        client.newCall(request).execute().use { response ->
            Log.d("!!!", "responseCode ${response.code}")

            val json = response.body?.string() ?: ""
            Log.d("!!!", "Рецепты для категории $categoryId: $json")
        }
    }

    fun forLessonExample() {
        val contentType = "application/json".toMediaType()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://recipes.androidsprint.ru/api/")
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()

        val service: RecipeApiService = retrofit.create(RecipeApiService::class.java)

        val categoriesCall: Call<List<Category>> = service.getCategories()
        val categoriesResponse: retrofit2.Response<List<Category>> = categoriesCall.execute()
        val categories: List<Category>? = categoriesResponse.body()

        Log.i("!!!", "categories ${categories.toString()}")
    }
}