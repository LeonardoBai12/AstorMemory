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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import io.lb.presentation.R
import io.lb.presentation.ui.components.MemoryGameRedButton
import io.lb.presentation.ui.components.MemoryGameWhiteButton
import io.lb.presentation.ui.navigation.MemoryGameScreens

@Composable
internal fun MenuScreen(
    navController: NavController,
    onClickQuit: () -> Unit = {}
) {
    val amount = remember {
        mutableIntStateOf(5)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.White
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = R.drawable.pokemon_game_logo),
                contentDescription = "Pokemon Memory Challenge",
            )

            Spacer(modifier = Modifier.height(72.dp))
            Text(
                text = "Select the amount of cards",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                MemoryGameWhiteButton(
                    modifier = Modifier,
                    text = "-"
                ) {
                    if (amount.intValue > 4) {
                        amount.intValue--
                    }
                }
                Spacer(modifier = Modifier.width(24.dp))
                Text(
                    text = amount.intValue.toString(),
                    fontSize = 72.sp
                )
                Spacer(modifier = Modifier.width(24.dp))
                MemoryGameWhiteButton(
                    modifier = Modifier,
                    text = "+"
                ) {
                    if (amount.intValue < 12) {
                        amount.intValue++
                    }
                }
            }

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
                        navController.currentBackStackEntry?.arguments?.putInt("amount", amount.intValue)
                        navController.navigate(MemoryGameScreens.Game.name)
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                MemoryGameWhiteButton(
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
    }
}

@Preview
@Composable
internal fun MenuScreenPreview() {
    val context = LocalContext.current
    MenuScreen(NavController(context))
}
