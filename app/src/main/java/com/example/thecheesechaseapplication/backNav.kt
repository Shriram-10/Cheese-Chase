package com.example.thecheesechaseapplication

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun BackNavigator(navController: NavController){
    BackHandler(enabled = true){
        navController.popBackStack(Screen.Settings.route, false)
    }
}