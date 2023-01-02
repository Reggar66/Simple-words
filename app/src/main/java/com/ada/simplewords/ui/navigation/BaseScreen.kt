package com.ada.simplewords.ui.navigation

abstract class BaseScreen(val route: String)

sealed class Root(route: String) : BaseScreen(route) {
    object QuizListRoot : Root("quizListRoot")
    object SignInRoot : Root("signInRoot")
}

sealed class Screen(route: String) : BaseScreen(route) {
    object QuizList : Screen("quizList")

    object Exercise : Screen(route = "exercise/{${Key.QUIZ_ID}}") {
        fun createRoute(quizId: String) = "exercise/$quizId"

        object Key {
            const val QUIZ_ID = "quizId"
        }
    }

    object Create : Screen(route = "create")

    object SignIn : Screen(route = "signIn")

    object Account : Screen(route = "account")
}
