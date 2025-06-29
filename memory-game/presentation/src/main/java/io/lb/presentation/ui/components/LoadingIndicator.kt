package io.lb.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.lb.presentation.R
import io.lb.presentation.ui.theme.PrimaryRed

@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier.fillMaxSize(),
    screenHeight: Dp
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
                .size(screenHeight / 6),
            color = PrimaryRed,
            strokeWidth = 5.dp,
        )
        Image(
            modifier = Modifier.size(screenHeight / 8),
            painter = painterResource(id = R.drawable.narcisus),
            contentDescription = "Loading",
        )
    }
}

