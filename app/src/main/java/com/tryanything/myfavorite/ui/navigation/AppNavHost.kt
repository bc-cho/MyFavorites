package com.tryanything.myfavorite.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tryanything.myfavorite.ui.screen.MapScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: Destination,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController,
        startDestination = startDestination.route
    ) {
        Destination.entries.forEach { destination ->
            composable(destination.route) {
                when (destination) {
                    Destination.MAP -> {
                        MapScreen()
                    }

                    Destination.FAVORITES -> {
                        MapScreen()
                    }
                }
            }
        }
    }
}
