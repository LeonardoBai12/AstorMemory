package io.lb.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import io.lb.presentation.menu.MenuScreen
import io.lb.presentation.ui.theme.PokemonMemoryChallengeTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokemonMemoryChallengeTheme {
                val navController = rememberNavController()
                MenuScreen(navController = navController)
            }
        }
    }
}
