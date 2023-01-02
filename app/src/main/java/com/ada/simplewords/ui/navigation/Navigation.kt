package com.ada.simplewords.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ada.account.AccountScreen
import com.ada.exercise.ExerciseScreen
import com.ada.quizcreate.CreateQuizScreen
import com.ada.quizlist.QuizListScreen
import com.ada.signin.SignInScreen

@Composable
fun NavigationHost(
    modifier: Modifier = Modifier, navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Root.SignInRoot.route,
        modifier = modifier
    ) {
        quizListRoot(navController)
        signInRoot(navController)
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
                },
                openAccount = {
                    navController.navigate(route = Screen.Account.route)
                }
            )
        }

        composable(Screen.Exercise.route) { navBackStackEntry ->
            val quizId =
                navBackStackEntry.arguments?.getString(Screen.Exercise.Key.QUIZ_ID)
            ExerciseScreen(quizId = quizId, onReturnClick = { navController.popBackStack() })
        }

        composable(Screen.Create.route) {
            CreateQuizScreen(closeScreen = { navController.popBackStack() })
        }

        composable(Screen.Account.route) {
            AccountScreen(
                onBackClick = { navController.popBackStack() },
                openSignInScreen = {
                    navController.navigate(Screen.SignIn.route) {
                        popUpTo(route = Screen.QuizList.route) {
                            inclusive = true
                        }
                    }
                })
        }
    }
}

fun NavGraphBuilder.signInRoot(navController: NavController) {
    navigation(startDestination = Screen.SignIn.route, route = Root.SignInRoot.route) {
        composable(route = Screen.SignIn.route) {
            SignInScreen(openQuizList = {
                navController.navigate(route = Screen.QuizList.route) {
                    // TODO: Uncomment once there is a way to log out and go back to login screen.
                    popUpTo(Screen.SignIn.route) {
                        inclusive = true
                    }
                }
            })
        }
    }
}