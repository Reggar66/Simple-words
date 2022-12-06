package com.ada.simplewords.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.ada.simplewords.feature.exercise.ExerciseScreen
import com.ada.simplewords.feature.quiz.create.CreateQuizScreen
import com.ada.simplewords.feature.quiz.list.QuizListScreen

@Composable
fun NavigationHost(
    modifier: Modifier = Modifier, navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Root.QuizListRoot.route,
        modifier = modifier
    ) {
        quizListRoot(navController)
    }
}

fun NavGraphBuilder.quizListRoot(navController: NavController) {
    navigation(startDestination = Screen.QuizList.route, route = Root.QuizListRoot.route) {
        composable(Screen.QuizList.route) {
            QuizListScreen(
                openExercise = { quiz ->
                    navController
                        .navigate(route = Screen.Exercise.createRoute(quizId = quiz.id))
                },
                openCreate = {
                    navController.navigate(route = Screen.Create.route)
                }
            )
        }

        composable(Screen.Exercise.route) { navBackStackEntry ->
            val quizId =
                navBackStackEntry.arguments?.getString(Screen.Exercise.Key.QUIZ_ID)
            ExerciseScreen(quizId = quizId)
        }

        composable(Screen.Create.route) {
            CreateQuizScreen(closeScreen = { navController.popBackStack() })
        }
    }
}

fun NavGraphBuilder.loginRoot(navController: NavController) {
    // TODO: Login nav root
}