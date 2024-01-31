package com.example.recipeapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.databinding.FragmentListCategoriesBinding
import data.STUB

class CategoriesListFragment : Fragment() {

    private var recyclerView: RecyclerView? = null

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
    }

    private fun openRecipesByCategoryId() {
        parentFragmentManager.commit {
            replace<RecipesListFragment>(R.id.mainContainer)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

    private fun initRecycler() {
        val adapter = CategoriesListAdapter(STUB.getCategories(), this)
        recyclerView = binding.rvCategories
        recyclerView?.adapter = adapter

        adapter.setOnItemClickListener(object : CategoriesListAdapter.OnItemClickListener {
            override fun onItemClick() {
                openRecipesByCategoryId()
            }
        })
    }
}