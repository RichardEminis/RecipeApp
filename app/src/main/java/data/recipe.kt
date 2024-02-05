package data

import model.Category

object STUB {

    private val burgerRecipes = listOf(
        "Классический гамбургер",
        "Чизбургер",
        "Бургер с грибами и сыром",
        "Бургер с курицей и авокадо",
        "бургер с рыбой",
        "Бургер с беконом",
        "Веганский бургер",
        "острый гамбургер"
    )

    private var categories: List<Category> = (listOf(
        Category(0, "Бургеры", "Рецепты всех популярных видов бургеров", "burger.png"),
        Category(1, "Десерты", "Самые вкусные рецепты десертов специально для вас", "dessert.png"),
        Category(2, "Пицца", "Пицца на любой вкус и цвет. Лучшая подборка для тебя", "pizza.png"),
        Category(3, "Рыба", "Печеная, жареная, сушеная, любая рыба на твой вкус", "fish.png"),
        Category(4, "Супы", "От классики до экзотики: мир в одной тарелке", "soup.png"),
        Category(5, "Салаты", "Хрустящий калейдоскоп под соусом вдохновения", "salad.png")
    ))

    fun getCategories() = categories
    fun getRecipesByCategoryId(categoryId: Int): List<String>? {
        return if (categoryId == 0) burgerRecipes
        else null
    }
}