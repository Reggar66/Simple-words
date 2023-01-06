package com.ada.simplewords

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ada.simplewords.ui.navigation.NavigationHost
import com.ada.simplewords.ui.navigation.Screen
import com.ada.ui.theme.SimpleWordsTheme

@Composable
fun MainScreen() {
    val navController: NavHostController = rememberNavController()
    SimpleWordsTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
            DebugOverlay(navController = navController) {
                NavigationHost(navController = navController)
            }
        }
    }
}

@Composable
private fun DebugOverlay(navController: NavController, content: @Composable () -> Unit) {

    val viewModel = hiltViewModel<DebugViewModel>()
    var showDebug by remember {
        mutableStateOf(false)
    }
    Box(modifier = Modifier.fillMaxSize()) {

        content()

        if (BuildConfig.DEBUG)
            Button(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp),
                onClick = { showDebug = !showDebug }) {
                Text(text = "Debug")
            }

        if (showDebug)
            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .padding(top = 60.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = { viewModel.createMockQuizAnimals() }) {
                    Text(text = "Generate Animals \uD83E\uDD8A")
                }

                Button(onClick = { viewModel.createMockQuizAnimalsCompleted() }) {
                    Text(text = "Generate Animals Completed")
                }

                Button(onClick = { viewModel.createMockQuizFood() }) {
                    Text(text = "Generate Food")
                }

                Button(onClick = { viewModel.createMockQuizSeasons() }) {
                    Text(text = "Generate Seasons")
                }

                Button(onClick = {
                    viewModel.signOut()
                    navController.navigate(Screen.Welcome.route) {
                        launchSingleTop = true
                        popUpTo(route = Screen.QuizList.route) {
                            inclusive = true
                        }
                    }
                }) {
                    Text(text = "Sign out")
                }
            }
    }
}