package com.tontowi0086.mobpro1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.tontowi0086.mobpro1.navigation.SetupNavGraph
import com.tontowi0086.mobpro1.ui.theme.Mobpro1Theme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            SetupNavGraph(navController)
        }
    }
}