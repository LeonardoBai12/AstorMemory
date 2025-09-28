package io.lb.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.lb.presentation.R

@Composable
fun MemoryGameLogo(
    modifier: Modifier = Modifier,
) {
    Image(
        modifier = modifier
            .aspectRatio(3f)
            .heightIn(min = 60.dp, max = 200.dp),
        painter = painterResource(
            id = R.drawable.astor_game_logo
        ),
        contentDescription = "Astor Memory Challenge",
        contentScale = ContentScale.Fit
    )
}

@Composable
fun Narcisus() {
    Image(
        modifier = Modifier
            .size(48.dp)
            .padding(4.dp),
        painter = painterResource(id = R.drawable.narcisus),
        contentDescription = stringResource(R.string.narcisus),
        contentScale = ContentScale.Fit
    )
}
