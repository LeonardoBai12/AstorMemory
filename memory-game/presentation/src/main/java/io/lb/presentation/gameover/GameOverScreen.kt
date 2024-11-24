package io.lb.presentation.gameover

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import io.lb.presentation.R
import io.lb.presentation.ui.components.MemoryGameLogo
import io.lb.presentation.ui.components.MemoryGameRedButton
import io.lb.presentation.ui.components.MemoryGameWhiteButton
import io.lb.presentation.ui.navigation.MemoryGameScreens

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
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            MemoryGameLogo(isDarkMode)
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.you_won),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(48.dp))

            if (score == 0) {
                Text(
                    text = stringResource(R.string.score),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(24.dp))
                Image(
                    modifier = Modifier.size(200.dp),
                    painter = painterResource(id = R.drawable.missingno),
                    contentDescription = "Missing Number reference",
                )
            } else {
                Text(
                    text = "Score: $score",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            GameOverButtons(navController, isDarkMode, amount)
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
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        if (amount != 0) {
            MemoryGameRedButton(
                text = stringResource(R.string.play_again),
                onClick = {
                    navController.navigate(MemoryGameScreens.Game.name + "/$amount")
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
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
        Spacer(modifier = Modifier.height(24.dp))
        Image(
            modifier = Modifier.size(48.dp),
            painter = painterResource(id = R.drawable.pokeball),
            contentDescription = "PokeBall",
        )
    }
}
