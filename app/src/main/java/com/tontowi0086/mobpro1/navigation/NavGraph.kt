package com.tontowi0086.mobpro1.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tontowi0086.mobpro1.ui.screen.AboutScreen
import com.tontowi0086.mobpro1.ui.screen.MainScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            MainScreen(navController)
        }
        composable("about") {
            AboutScreen(navController)
        }
    }
}