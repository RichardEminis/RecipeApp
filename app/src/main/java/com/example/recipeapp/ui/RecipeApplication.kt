package com.example.recipeapp.ui

import android.app.Application
import com.example.recipeapp.di.AppContainer

class RecipeApplication: Application() {

    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()

        appContainer = AppContainer(this)
    }
}