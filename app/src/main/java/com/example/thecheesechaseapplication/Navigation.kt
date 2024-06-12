package com.example.thecheesechaseapplication

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation(modifier: Modifier){
    val navController = rememberNavController()
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
                highScore = HighScoreManager(LocalContext.current)
            )
        }
        composable(
            route = Screen.Game.route
        ) {
            Game(
                modifier = modifier,
                navController = navController,
                highScore = HighScoreManager(LocalContext.current)
            )
        }
    }
}