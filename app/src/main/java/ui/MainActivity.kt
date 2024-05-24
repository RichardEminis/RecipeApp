package ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.recipeapp.R
import com.example.recipeapp.databinding.ActivityMainBinding
import kotlinx.serialization.json.Json
import model.Category
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for ActivityMainBinding must not be null")

    private val threadPool = Executors.newFixedThreadPool(10)

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

            val url = URL("https://recipes.androidsprint.ru/api/category")
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.connect()

            Log.d("!!!", "responseCode ${connection.responseCode}")

            val json = connection.inputStream.bufferedReader().readText()
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
        thread.start()
    }

    private fun getRecipesFromUrl(categoryId: Int) {
        Log.d("!!!", "Выполняю запрос рецептов для категории $categoryId на потоке: ${Thread.currentThread().name}")

        val url = URL("https://recipes.androidsprint.ru/api/category/$categoryId/recipes")
        val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
        connection.connect()

        val json = connection.inputStream.bufferedReader().readText()
        Log.d("!!!", "Рецепты для категории $categoryId: $json")
    }
}