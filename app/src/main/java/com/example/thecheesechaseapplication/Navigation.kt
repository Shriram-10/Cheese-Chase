package com.example.thecheesechaseapplication

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(modifier: Modifier){
    val navController = rememberNavController()
    val dataViewModel : MainViewModel = viewModel()
    val viewState by dataViewModel.state
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ){
        composable(
            route = Screen.Home.route
        ){
            Home(
                modifier = modifier,
                navController = navController,
            )
        }
        composable(
            route = Screen.Settings.route
        ) {
            Settings(
                modifier = modifier,
                navController = navController,
                highScore = HighScoreManager(LocalContext.current),
                dataViewModel = dataViewModel
            )
        }
        composable(
            route = Screen.Load.route
        ){
            LoadingPage(
                modifier = modifier,
                navController = navController,
                dataViewModel = dataViewModel
            )
        }
        composable(
            route = Screen.Game.route
        ) {
            Game(
                modifier = modifier,
                navController = navController,
                highScore = HighScoreManager(LocalContext.current),
                context = LocalContext.current,
                dataViewModel = dataViewModel
            )
        }
    }
}