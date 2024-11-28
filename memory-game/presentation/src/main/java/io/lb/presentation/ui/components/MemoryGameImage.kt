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
    modifier: Modifier = Modifier.fillMaxWidth(0.9f),
) {
    Image(
        modifier = modifier,
        painter = painterResource(
            id = if (isDarkMode) {
                R.drawable.pokemon_game_logo_b
            } else {
                R.drawable.pokemon_game_logo_w
            }
        ),
        contentDescription = "Pokemon Memory Challenge",
    )
}
