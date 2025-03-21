package com.tontowi0086.mobpro1.navigation

sealed class Screen (val route: String){
    data object Home: Screen("mainscreen")
    data object About: Screen("aboutscreen")

}