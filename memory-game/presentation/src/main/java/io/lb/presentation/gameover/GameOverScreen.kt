package io.lb.presentation.gameover

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import io.lb.presentation.R
import io.lb.presentation.ui.components.MemoryGameLogo
import io.lb.presentation.ui.components.MemoryGameRedButton
import io.lb.presentation.ui.components.MemoryGameWhiteButton
import io.lb.presentation.ui.components.Narcisus
import io.lb.presentation.ui.navigation.MemoryGameScreens
import io.lb.presentation.ui.theme.AstorMemoryChallengeTheme

@Composable
fun GameOverScreen(
    navController: NavController,
    isDarkMode: Boolean,
    score: Int,
    amount: Int,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            MemoryGameLogo(
                Modifier
                    .fillMaxWidth(0.6f)
                    .heightIn(min = 60.dp, max = 120.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = stringResource(R.string.you_won),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            if (score == 0) {
                Text(
                    text = stringResource(R.string.score),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(24.dp))
                Image(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .aspectRatio(1f)
                        .sizeIn(minWidth = 120.dp, maxWidth = 200.dp, minHeight = 120.dp, maxHeight = 200.dp),
                    painter = painterResource(id = R.drawable.missingno),
                    contentDescription = "Missing Number reference",
                    contentScale = ContentScale.Fit
                )
            } else {
                Text(
                    text = stringResource(R.string.score_result, score),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.with_card_pairs, amount),
                    style = MaterialTheme.typography.titleLarge, // Scalable typography
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            GameOverButtons(navController, isDarkMode, amount)

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun GameOverButtons(
    navController: NavController,
    isDarkMode: Boolean,
    amount: Int
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (amount != 0) {
            MemoryGameRedButton(
                text = stringResource(R.string.play_again),
                onClick = {
                    navController.navigate(MemoryGameScreens.Game.name + "/$amount")
                }
            )
        }

        MemoryGameWhiteButton(
            text = stringResource(R.string.back),
            isDarkMode = isDarkMode,
            onClick = {
                navController.navigate(MemoryGameScreens.Menu.name) {
                    popUpTo(MemoryGameScreens.Menu.name) {
                        inclusive = true
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))
        Narcisus()
    }
}

@Preview(name = "Light Mode - Won", showBackground = true)
@Composable
internal fun GameOverScreenPreview() {
    AstorMemoryChallengeTheme(
        darkTheme = false
    ) {
        GameOverScreen(
            navController = rememberNavController(),
            isDarkMode = false,
            score = 1250,
            amount = 8
        )
    }
}

@Preview(name = "Dark Mode - Won", showBackground = true)
@Composable
internal fun GameOverScreenDarkPreview() {
    AstorMemoryChallengeTheme(
        darkTheme = true
    ) {
        GameOverScreen(
            navController = rememberNavController(),
            isDarkMode = true,
            score = 980,
            amount = 12
        )
    }
}

@Preview(name = "Score Zero - MissingNo", showBackground = true)
@Composable
internal fun GameOverScreenZeroScorePreview() {
    AstorMemoryChallengeTheme(
        darkTheme = false
    ) {
        GameOverScreen(
            navController = rememberNavController(),
            isDarkMode = false,
            score = 0,
            amount = 5
        )
    }
}

@Preview(name = "High Score", showBackground = true)
@Composable
internal fun GameOverScreenHighScorePreview() {
    AstorMemoryChallengeTheme(
        darkTheme = false
    ) {
        GameOverScreen(
            navController = rememberNavController(),
            isDarkMode = false,
            score = 5420,
            amount = 25
        )
    }
}

@Preview(name = "Large Font", showBackground = true, fontScale = 1.5f)
@Composable
internal fun GameOverScreenLargeFontPreview() {
    AstorMemoryChallengeTheme(
        darkTheme = false
    ) {
        GameOverScreen(
            navController = rememberNavController(),
            isDarkMode = false,
            score = 1750,
            amount = 15
        )
    }
}

@Preview(name = "Small Screen", showBackground = true, widthDp = 320, heightDp = 480)
@Composable
internal fun GameOverScreenSmallPreview() {
    AstorMemoryChallengeTheme(
        darkTheme = false
    ) {
        GameOverScreen(
            navController = rememberNavController(),
            isDarkMode = false,
            score = 890,
            amount = 6
        )
    }
}

@Preview(name = "Small Screen - Zero Score", showBackground = true, widthDp = 320, heightDp = 480)
@Composable
internal fun GameOverScreenSmallZeroScorePreview() {
    AstorMemoryChallengeTheme(
        darkTheme = false
    ) {
        GameOverScreen(
            navController = rememberNavController(),
            isDarkMode = false,
            score = 0,
            amount = 3
        )
    }
}

@Preview(name = "Amount Zero - No Play Again", showBackground = true)
@Composable
internal fun GameOverScreenNoPlayAgainPreview() {
    AstorMemoryChallengeTheme(
        darkTheme = false
    ) {
        GameOverScreen(
            navController = rememberNavController(),
            isDarkMode = false,
            score = 1100,
            amount = 0
        )
    }
}