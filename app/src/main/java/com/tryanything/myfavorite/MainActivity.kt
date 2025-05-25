package com.tryanything.myfavorite

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import com.tryanything.myfavorite.ui.navigation.NavigationTabScreen
import com.tryanything.myfavorite.ui.theme.MyFavoritesTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyFavoritesTheme {
                NavigationTabScreen(Modifier)
            }
        }
    }
}
