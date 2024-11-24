package io.lb.presentation.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import io.lb.presentation.R
import io.lb.presentation.ui.components.IntSelector
import io.lb.presentation.ui.components.MemoryGameBlueButton
import io.lb.presentation.ui.components.MemoryGameLogo
import io.lb.presentation.ui.components.MemoryGameRedButton
import io.lb.presentation.ui.components.MemoryGameWhiteButton
import io.lb.presentation.ui.navigation.MemoryGameScreens
import io.lb.presentation.ui.theme.PokemonMemoryChallengeTheme

@Composable
internal fun MenuScreen(
    navController: NavController,
    isDarkMode: Boolean,
    initialAmount: Int,
    onClickQuit: () -> Unit,
    onChangeAmount: (Int) -> Unit
) {
    val amount = remember {
        mutableIntStateOf(initialAmount)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                onClick = {
                    navController.navigate(MemoryGameScreens.Settings.name)
                },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = Color.Gray,
                    modifier = Modifier.size(48.dp)
                )
            }
        }
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            MemoryGameLogo(isDarkMode)

            Spacer(modifier = Modifier.height(72.dp))
            Text(
                text = "Amount of card pairs",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            IntSelector(
                intState = amount,
                minValue = 1,
                maxValue = 30,
                isDarkMode = isDarkMode,
                onChangeAmount = onChangeAmount
            )

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                modifier = Modifier.fillMaxWidth(0.8f),
                text = "The more cards you play with, the harder the game will be",
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            ButtonsColumn(
                navController,
                isDarkMode,
                amount,
                onClickQuit
            )
        }
    }
}

@Composable
private fun ButtonsColumn(
    navController: NavController,
    isDarkMode: Boolean,
    amount: MutableIntState,
    onClickQuit: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        MemoryGameRedButton(
            text = "START GAME",
            onClick = {
                navController.navigate(MemoryGameScreens.Game.name + "/${amount.intValue}")
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        MemoryGameBlueButton(
            text = "HIGHSCORES",
            onClick = {
                navController.navigate(MemoryGameScreens.HighScores.name)
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        MemoryGameWhiteButton(
            isDarkMode = isDarkMode,
            text = "QUIT",
            onClick = {
                onClickQuit()
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

@Preview
@Composable
internal fun MenuScreenPreview() {
    val context = LocalContext.current
    PokemonMemoryChallengeTheme(
        darkTheme = false
    ) {
        MenuScreen(NavController(context), true,5, {}, {})
    }
}
