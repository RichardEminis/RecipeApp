package com.example.recipeapp

data class Category(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String
)

data class Ingredient(
    val quantity: Int,
    val unitOfMeasure: String,
    val description: String
)

data class Recipe(
    val id: Int,
    val title: String,
    val listOfIngredients: List<Ingredient>,
    val method: String,
    val imageUrl: String
)