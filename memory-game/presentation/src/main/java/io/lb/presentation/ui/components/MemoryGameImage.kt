package io.lb.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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

@Composable
fun PokeBall() {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    Image(
        modifier = Modifier.size(screenHeight.dp / 16),
        painter = painterResource(id = R.drawable.pokeball),
        contentDescription = stringResource(R.string.pokeball),
    )
}
