package com.example.thecheesechaseapplication

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun LoadingPage(modifier : Modifier, navController : NavController, dataViewModel : MainViewModel){
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        CircularProgressIndicator()

        val viewState by dataViewModel.state
        dataViewModel.fetchObstacleLimit()

        if (viewState.value != null && !viewState.loading && viewState.error == null){
            navController.navigate(Screen.Game.route)
        }
    }
}