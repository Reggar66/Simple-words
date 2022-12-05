package com.ada.simplewords.feature.main

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
import com.ada.simplewords.BuildConfig
import com.ada.simplewords.feature.debug.DebugViewModel
import com.ada.simplewords.ui.navigation.NavigationHost
import com.ada.simplewords.ui.theme.SimpleWordsTheme

@Composable
fun MainScreen() {
    SimpleWordsTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
            DebugContainer {
                NavigationHost()
            }
        }
    }
}

@Composable
private fun DebugContainer(content: @Composable () -> Unit) {

    val viewModel = hiltViewModel<DebugViewModel>()
    var showDebug by remember {
        mutableStateOf(false)
    }
    Box(modifier = Modifier.fillMaxSize()) {

        content()

        if (BuildConfig.DEBUG)
            Button(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(4.dp),
                onClick = { showDebug = !showDebug }) {
                Text(text = "Debug")
            }

        if (showDebug)
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = { viewModel.createMockUser() }) {
                    Text(text = "Generate Mock User")
                }

                Button(onClick = { viewModel.createMockQuizAnimals() }) {
                    Text(text = "Generate Animals")
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
            }
    }
}