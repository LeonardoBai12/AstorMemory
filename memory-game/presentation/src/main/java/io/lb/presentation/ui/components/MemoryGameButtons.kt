package io.lb.presentation.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.lb.presentation.R
import io.lb.presentation.ui.theme.PrimaryRed

@Composable
internal fun MemoryGameButtonWithBackground(
    @DrawableRes backgroundDrawable: Int,
    text: String? = null,
    textColor: Color? = null,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = backgroundDrawable),
            contentDescription = null,
        )

        if (text != null) {
            Text(
                text = text,
                color = textColor ?: MaterialTheme.colorScheme.onBackground,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
internal fun MemoryGameRedButton(
    text: String,
    onClick: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    MemoryGameButtonWithBackground(
        modifier = Modifier.height(screenHeight.dp / 16),
        backgroundDrawable = R.drawable.rebbutton,
        text = text,
        textColor = Color.White,
        onClick = onClick
    )
}

@Composable
internal fun MemoryGameStopButton(
    onClick: () -> Unit
) {
    MemoryGameButtonWithBackground(
        modifier = Modifier.height(48.dp),
        backgroundDrawable = R.drawable.closebutton,
        onClick = {
            onClick()
        }
    )
}

@Composable
internal fun MemoryGameRestartButton(
    onClick: () -> Unit
) {
    MemoryGameButtonWithBackground(
        modifier = Modifier.height(48.dp),
        backgroundDrawable = R.drawable.refreshbutton,
        onClick = {
            onClick()
        }
    )
}

@Composable
fun MemoryGameBackButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    MemoryGameButtonWithBackground(
        modifier = modifier.height(48.dp),
        backgroundDrawable = R.drawable.backbutton,
        onClick = {
            onClick()
        }
    )
}

@Composable
fun MemoryGamePlusButton(
    isDarkMode: Boolean,
    onClick: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    MemoryGameButtonWithBackground(
        modifier = Modifier.height(screenHeight.dp / 16),
        backgroundDrawable = if (isDarkMode) R.drawable.plusbutton_b else R.drawable.plusbutton_w,
        onClick = {
            onClick()
        }
    )
}

@Composable
fun MemoryGameMinusButton(
    isDarkMode: Boolean,
    onClick: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    MemoryGameButtonWithBackground(
        modifier = Modifier.height(screenHeight.dp / 16),
        backgroundDrawable = if (isDarkMode) R.drawable.minusbutton_b else R.drawable.minusbutton_w,
        onClick = {
            onClick()
        }
    )
}

@Composable
internal fun MemoryGameBlueButton(
    text: String,
    onClick: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    MemoryGameButtonWithBackground(
        modifier = Modifier.height(screenHeight.dp / 16),
        backgroundDrawable = R.drawable.bluebutton,
        text = text,
        textColor = Color.White,
        onClick = onClick
    )
}

@Composable
internal fun MemoryGameWhiteButton(
    isDarkMode: Boolean = isSystemInDarkTheme(),
    text: String,
    onClick: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    MemoryGameButtonWithBackground(
        modifier = Modifier.height(screenHeight.dp / 16),
        backgroundDrawable = if (isDarkMode) R.drawable.blackbutton else R.drawable.whitebutton,
        text = text,
        textColor =  PrimaryRed,
        onClick = onClick
    )
}
