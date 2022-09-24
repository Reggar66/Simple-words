package com.example.simplewords.ui.navigation

typealias SimpleNavigation = () -> Unit

abstract class BaseScreen(val route: String)

sealed class Root(route: String) : BaseScreen(route) {
    object QuizListRoot : Root("quizListRoot")
}

sealed class Screen(route: String) : BaseScreen(route) {
    object QuizList : Screen("list")
    object QuizDetails : Screen("quiz")
}
