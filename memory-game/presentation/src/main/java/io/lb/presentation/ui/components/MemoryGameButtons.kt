package io.lb.presentation.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.lb.presentation.R
import io.lb.presentation.ui.theme.AstorMemoryChallengeTheme
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
            .clickable { onClick() }
            .defaultMinSize(minHeight = 48.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = backgroundDrawable),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = if (text == null) ContentScale.Fit else ContentScale.FillBounds
        )

        if (text != null) {
            Text(
                text = text,
                color = textColor ?: MaterialTheme.colorScheme.onBackground,
                fontSize = with(LocalDensity.current) {
                    minOf(24f, (24 * fontScale)).sp
                },
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
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
    val density = LocalDensity.current

    val buttonHeight = with(density) {
        val baseHeight = screenHeight.dp / 16
        val fontScaledHeight = baseHeight * fontScale
        minOf(fontScaledHeight, 80.dp).coerceAtLeast(56.dp)
    }

    MemoryGameButtonWithBackground(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(buttonHeight),
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
        modifier = Modifier.size(48.dp),
        backgroundDrawable = R.drawable.closebutton,
        onClick = onClick
    )
}

@Composable
internal fun MemoryGameRestartButton(
    onClick: () -> Unit
) {
    MemoryGameButtonWithBackground(
        modifier = Modifier.size(48.dp),
        backgroundDrawable = R.drawable.refreshbutton,
        onClick = onClick
    )
}

@Composable
fun MemoryGameBackButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    MemoryGameButtonWithBackground(
        modifier = modifier.size(48.dp),
        backgroundDrawable = R.drawable.backbutton,
        onClick = onClick
    )
}

@Composable
fun MemoryGamePlusButton(
    isDarkMode: Boolean,
    onClick: () -> Unit
) {
    val density = LocalDensity.current

    val buttonSize = with(density) {
        val baseSize = 56.dp
        val fontScaledSize = baseSize * fontScale
        minOf(fontScaledSize, 72.dp).coerceAtLeast(48.dp)
    }

    MemoryGameButtonWithBackground(
        modifier = Modifier.size(buttonSize),
        backgroundDrawable = if (isDarkMode) R.drawable.plusbutton_b else R.drawable.plusbutton_w,
        onClick = onClick
    )
}

@Composable
fun MemoryGameMinusButton(
    isDarkMode: Boolean,
    onClick: () -> Unit
) {
    val density = LocalDensity.current

    val buttonSize = with(density) {
        val baseSize = 56.dp
        val fontScaledSize = baseSize * fontScale
        minOf(fontScaledSize, 72.dp).coerceAtLeast(48.dp)
    }

    MemoryGameButtonWithBackground(
        modifier = Modifier.size(buttonSize),
        backgroundDrawable = if (isDarkMode) R.drawable.minusbutton_b else R.drawable.minusbutton_w,
        onClick = onClick
    )
}

@Composable
internal fun MemoryGameBlueButton(
    text: String,
    onClick: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val density = LocalDensity.current

    val buttonHeight = with(density) {
        val baseHeight = screenHeight.dp / 16
        val fontScaledHeight = baseHeight * fontScale
        minOf(fontScaledHeight, 80.dp).coerceAtLeast(56.dp)
    }

    MemoryGameButtonWithBackground(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(buttonHeight),
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
    val density = LocalDensity.current

    val buttonHeight = with(density) {
        val baseHeight = screenHeight.dp / 16
        val fontScaledHeight = baseHeight * fontScale
        minOf(fontScaledHeight, 80.dp).coerceAtLeast(56.dp)
    }

    MemoryGameButtonWithBackground(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(buttonHeight),
        backgroundDrawable = if (isDarkMode) R.drawable.blackbutton else R.drawable.whitebutton,
        text = text,
        textColor = PrimaryRed,
        onClick = onClick
    )
}

@Preview(name = "Red Buttons - Normal Text", showBackground = true)
@Composable
internal fun RedButtonNormalPreview() {
    AstorMemoryChallengeTheme(darkTheme = false) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MemoryGameRedButton(
                text = "Start Game",
                onClick = { }
            )
            MemoryGameRedButton(
                text = "Play Again",
                onClick = { }
            )
            MemoryGameRedButton(
                text = "Try Again",
                onClick = { }
            )
        }
    }
}

@Preview(name = "Red Buttons - Long Text", showBackground = true)
@Composable
internal fun RedButtonLongTextPreview() {
    AstorMemoryChallengeTheme(darkTheme = false) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MemoryGameRedButton(
                text = "Start New Memory Challenge Game",
                onClick = { }
            )
            MemoryGameRedButton(
                text = "Continue Playing This Awesome Memory Game",
                onClick = { }
            )
            MemoryGameRedButton(
                text = "This is a very long button text that should test overflow handling",
                onClick = { }
            )
        }
    }
}

@Preview(name = "Blue Buttons - Various Text", showBackground = true)
@Composable
internal fun BlueButtonPreview() {
    AstorMemoryChallengeTheme(darkTheme = false) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MemoryGameBlueButton(
                text = "Highscores",
                onClick = { }
            )
            MemoryGameBlueButton(
                text = "View All High Scores",
                onClick = { }
            )
            MemoryGameBlueButton(
                text = "Check Out The Leaderboard And Rankings",
                onClick = { }
            )
        }
    }
}

@Preview(name = "White Buttons - Light Mode", showBackground = true)
@Composable
internal fun WhiteButtonLightPreview() {
    AstorMemoryChallengeTheme(darkTheme = false) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MemoryGameWhiteButton(
                isDarkMode = false,
                text = "Back",
                onClick = { }
            )
            MemoryGameWhiteButton(
                isDarkMode = false,
                text = "Go Back to Menu",
                onClick = { }
            )
            MemoryGameWhiteButton(
                isDarkMode = false,
                text = "Return to Main Menu Screen",
                onClick = { }
            )
            MemoryGameWhiteButton(
                isDarkMode = false,
                text = "Quit",
                onClick = { }
            )
        }
    }
}

@Preview(name = "White Buttons - Dark Mode", showBackground = true)
@Composable
internal fun WhiteButtonDarkPreview() {
    AstorMemoryChallengeTheme(darkTheme = true) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MemoryGameWhiteButton(
                isDarkMode = true,
                text = "Back",
                onClick = { }
            )
            MemoryGameWhiteButton(
                isDarkMode = true,
                text = "Go Back to Menu",
                onClick = { }
            )
            MemoryGameWhiteButton(
                isDarkMode = true,
                text = "Return to Main Menu Screen",
                onClick = { }
            )
        }
    }
}

@Preview(name = "Icon Buttons - Light Mode", showBackground = true)
@Composable
internal fun IconButtonsLightPreview() {
    AstorMemoryChallengeTheme(darkTheme = false) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Icon Buttons",
                style = MaterialTheme.typography.headlineSmall
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                MemoryGameStopButton(onClick = { })
                MemoryGameRestartButton(onClick = { })
                MemoryGameBackButton(onClick = { })
            }

            Text(
                text = "Plus/Minus Buttons",
                style = MaterialTheme.typography.titleMedium
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                MemoryGameMinusButton(
                    isDarkMode = false,
                    onClick = { }
                )
                Text(
                    text = "5",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                MemoryGamePlusButton(
                    isDarkMode = false,
                    onClick = { }
                )
            }
        }
    }
}

@Preview(name = "Icon Buttons - Dark Mode", showBackground = true)
@Composable
internal fun IconButtonsDarkPreview() {
    AstorMemoryChallengeTheme(darkTheme = true) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Icon Buttons - Dark",
                style = MaterialTheme.typography.headlineSmall
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                MemoryGameStopButton(onClick = { })
                MemoryGameRestartButton(onClick = { })
                MemoryGameBackButton(onClick = { })
            }

            Text(
                text = "Plus/Minus Buttons - Dark",
                style = MaterialTheme.typography.titleMedium
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                MemoryGameMinusButton(
                    isDarkMode = true,
                    onClick = { }
                )
                Text(
                    text = "12",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                MemoryGamePlusButton(
                    isDarkMode = true,
                    onClick = { }
                )
            }
        }
    }
}

@Preview(name = "All Buttons - Large Font", showBackground = true, fontScale = 1.5f)
@Composable
internal fun ButtonsLargeFontPreview() {
    AstorMemoryChallengeTheme(darkTheme = false) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Large Font Test",
                style = MaterialTheme.typography.headlineSmall
            )

            MemoryGameRedButton(
                text = "Start Game",
                onClick = { }
            )

            MemoryGameRedButton(
                text = "Start New Memory Challenge",
                onClick = { }
            )

            MemoryGameBlueButton(
                text = "View Highscores",
                onClick = { }
            )

            MemoryGameWhiteButton(
                isDarkMode = false,
                text = "Back to Main Menu",
                onClick = { }
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                MemoryGameMinusButton(
                    isDarkMode = false,
                    onClick = { }
                )
                Text(
                    text = "25",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                MemoryGamePlusButton(
                    isDarkMode = false,
                    onClick = { }
                )
            }
        }
    }
}

@Preview(name = "Buttons - Small Screen", showBackground = true, widthDp = 320, heightDp = 480)
@Composable
internal fun ButtonsSmallScreenPreview() {
    AstorMemoryChallengeTheme(darkTheme = false) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Small Screen (320dp)",
                style = MaterialTheme.typography.titleMedium
            )

            MemoryGameRedButton(
                text = "Start",
                onClick = { }
            )

            MemoryGameRedButton(
                text = "Start New Game",
                onClick = { }
            )

            MemoryGameBlueButton(
                text = "Scores",
                onClick = { }
            )

            MemoryGameWhiteButton(
                isDarkMode = false,
                text = "Back",
                onClick = { }
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MemoryGameStopButton(onClick = { })
                MemoryGameRestartButton(onClick = { })
                MemoryGameBackButton(onClick = { })
            }
        }
    }
}

@Preview(name = "Extreme Text Cases", showBackground = true)
@Composable
internal fun ButtonsExtremeTextPreview() {
    AstorMemoryChallengeTheme(darkTheme = false) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Extreme Text Length Tests",
                style = MaterialTheme.typography.headlineSmall
            )

            MemoryGameRedButton(
                text = "A",
                onClick = { }
            )

            MemoryGameRedButton(
                text = "This is an extremely long button text that should definitely test the overflow handling and ellipsis functionality",
                onClick = { }
            )

            MemoryGameBlueButton(
                text = "SUPERCALIFRAGILISTICEXPIALIDOCIOUS",
                onClick = { }
            )

            MemoryGameWhiteButton(
                isDarkMode = false,
                text = "Antidisestablishmentarianism",
                onClick = { }
            )

            MemoryGameRedButton(
                text = "🎮 Play Game 🎯",
                onClick = { }
            )
        }
    }
}

@Preview(name = "Button States Comparison", showBackground = true)
@Composable
internal fun ButtonStatesComparisonPreview() {
    AstorMemoryChallengeTheme(darkTheme = false) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Light Theme",
                    style = MaterialTheme.typography.titleMedium
                )

                MemoryGameRedButton(
                    text = "Red Button",
                    onClick = { }
                )

                MemoryGameBlueButton(
                    text = "Blue Button",
                    onClick = { }
                )

                MemoryGameWhiteButton(
                    isDarkMode = false,
                    text = "White Button",
                    onClick = { }
                )

                Row {
                    MemoryGameMinusButton(isDarkMode = false, onClick = { })
                    Spacer(modifier = Modifier.width(8.dp))
                    MemoryGamePlusButton(isDarkMode = false, onClick = { })
                }
            }

            Box(
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight()
                    .background(Color.Gray)
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Dark Theme",
                    style = MaterialTheme.typography.titleMedium
                )

                MemoryGameRedButton(
                    text = "Red Button",
                    onClick = { }
                )

                MemoryGameBlueButton(
                    text = "Blue Button",
                    onClick = { }
                )

                MemoryGameWhiteButton(
                    isDarkMode = true,
                    text = "Black Button",
                    onClick = { }
                )

                Row {
                    MemoryGameMinusButton(isDarkMode = true, onClick = { })
                    Spacer(modifier = Modifier.width(8.dp))
                    MemoryGamePlusButton(isDarkMode = true, onClick = { })
                }
            }
        }
    }
}
