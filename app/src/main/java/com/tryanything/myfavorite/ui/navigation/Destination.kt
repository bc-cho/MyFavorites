package com.tryanything.myfavorite.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Map
import androidx.compose.ui.graphics.vector.ImageVector

enum class Destination(
    val route: String,
    val label: String,
    val icon: ImageVector,
    val contentDescription: String
) {
    MAP("map", "MAP", Icons.Default.Map, "MAP"),
    FAVORITES("favorites", "Favorites", Icons.Default.Favorite, "POI List")
}
