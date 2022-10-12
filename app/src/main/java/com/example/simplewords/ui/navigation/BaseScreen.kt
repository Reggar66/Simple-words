package com.example.simplewords.ui.navigation

typealias SimpleNavigation = () -> Unit

typealias SimpleNavigationParam<T> = (T) -> Unit

abstract class BaseScreen(val route: String)

sealed class Root(route: String) : BaseScreen(route) {
    object QuizListRoot : Root("quizListRoot")
}

sealed class Screen(route: String) : BaseScreen(route) {
    object QuizList : Screen("quizList")
    object QuizDetails : Screen("quizDetails/{${Key.QUIZ_ID}}") {
        fun createRoute(quizId: Int) = "quizDetails/$quizId"

        object Key {
            const val QUIZ_ID = "quizId"
        }
    }
}
