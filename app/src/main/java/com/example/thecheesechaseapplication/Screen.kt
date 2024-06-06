package com.example.thecheesechaseapplication

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Settings : Screen("settings")
    object Game : Screen("game")
}