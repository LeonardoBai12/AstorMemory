package io.lb.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import io.lb.presentation.R

@Composable
fun MemoryGameLogo(
    isDarkMode: Boolean,
) {
    if (isDarkMode) {
        Image(
            modifier = Modifier.fillMaxWidth(0.9f),
            painter = painterResource(id = R.drawable.pokemon_game_logo_b),
            contentDescription = "Pokemon Memory Challenge",
        )
    } else {
        Image(
            modifier = Modifier.fillMaxWidth(0.9f),
            painter = painterResource(id = R.drawable.pokemon_game_logo_w),
            contentDescription = "Pokemon Memory Challenge",
        )
    }
}
