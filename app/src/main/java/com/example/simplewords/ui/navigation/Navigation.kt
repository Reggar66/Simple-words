package com.example.simplewords.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.simplewords.feature.quiz.list.QuizListScreen
import com.example.simplewords.feature.quiz.details.QuizDetailsScreen

@Composable
fun NavigationHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Root.QuizListRoot.route,
        modifier = modifier
    ) {
        quizListRoot(navController)
        quizDetailsRoot(navController)
    }
}

fun NavGraphBuilder.quizListRoot(navController: NavController) {
    navigation(startDestination = Screen.QuizList.route, route = Root.QuizListRoot.route) {
        composable(Screen.QuizList.route) {
            QuizListScreen(openQuiz = { navController.navigate(Screen.QuizDetails.createRoute(it.quizItem.id)) })
        }

        composable(Screen.QuizDetails.route) { navBackStackEntry ->
            QuizDetailsScreen(
                quizId = navBackStackEntry.arguments?.getString(Screen.QuizDetails.Key.QUIZ_ID)
                    ?.toInt()
            )
        }
    }
}

fun NavGraphBuilder.quizDetailsRoot(navController: NavController) {
    /* TODO */
}