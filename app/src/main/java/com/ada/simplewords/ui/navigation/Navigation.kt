package com.ada.simplewords.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ada.account.AccountScreen
import com.ada.accountdelete.DeleteAccountScreen
import com.ada.changepassword.ChangePasswordScreen
import com.ada.exercise.ExerciseScreen
import com.ada.quizcreate.CreateQuizScreen
import com.ada.quizedit.QuizEditScreen
import com.ada.quizlist.QuizListScreen
import com.ada.signin.SignInScreen
import com.ada.signup.SignUpScreen
import com.ada.welcome.WelcomeScreen

@Composable
fun NavigationHost(
    modifier: Modifier = Modifier, navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Root.WelcomeRoot.route,
        modifier = modifier
    ) {
        quizListRoot(navController)
        welcomeRoot(navController)
        accountRoot(navController)
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
                },
                openQuizEdit = { quiz ->
                    navController.navigate(Screen.QuizEdit.createRoute(quiz.id))
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

        composable(route = Screen.QuizEdit.route) { navBackStackEntry ->
            val quizId = navBackStackEntry.arguments?.getString(Screen.Exercise.Key.QUIZ_ID)
            QuizEditScreen(
                quizId = quizId,
                closeScreen = { navController.popBackStack() }
            )
        }
    }
}

fun NavGraphBuilder.accountRoot(navController: NavController) {
    navigation(startDestination = Screen.Account.route, route = Root.AccountRoot.route) {
        composable(Screen.Account.route) {
            AccountScreen(
                onBackClick = { navController.popBackStack() },
                openWelcomeScreen = {
                    navController.navigate(Screen.Welcome.route) {
                        launchSingleTop = true
                        popUpTo(route = Screen.QuizList.route) {
                            inclusive = true
                        }
                    }
                },
                openSignUpScreen = { navController.navigate(Screen.SignUp.route) },
                openChangePasswordScreen = { navController.navigate(Screen.ChangePassword.route) },
                openDeleteAccountScreen = { navController.navigate(Screen.DeleteAccount.route) }
            )
        }

        composable(Screen.ChangePassword.route) {
            ChangePasswordScreen(closeScreen = { navController.popBackStack() })
        }

        composable(Screen.DeleteAccount.route) {
            DeleteAccountScreen(
                closeScreen = { navController.popBackStack() },
                openWelcomeScreen = {
                    navController.navigate(Screen.Welcome.route) {
                        launchSingleTop = true
                        popUpTo(Screen.QuizList.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}

fun NavGraphBuilder.welcomeRoot(navController: NavController) {
    navigation(startDestination = Screen.Welcome.route, route = Root.WelcomeRoot.route) {
        composable(route = Screen.Welcome.route) {
            WelcomeScreen(
                openSignIn = { navController.navigate(Screen.SignIn.route) },
                openSignUp = { navController.navigate(Screen.SignUp.route) },
                openQuizList = {
                    navController.navigate(Screen.QuizList.route) {
                        launchSingleTop = true
                        popUpTo(Screen.Welcome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(route = Screen.SignIn.route) {
            SignInScreen(
                closeScreen = { navController.popBackStack() },
                openQuizList = {
                    navController.navigate(route = Screen.QuizList.route) {
                        launchSingleTop = true
                        popUpTo(Screen.Welcome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(route = Screen.SignUp.route) {
            SignUpScreen(
                closeScreen = { navController.popBackStack() },
                openQuizList = {
                    navController.navigate(Screen.QuizList.route) {
                        launchSingleTop = true
                        popUpTo(Screen.Welcome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}