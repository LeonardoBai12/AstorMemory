package io.lb.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import io.lb.presentation.R
import io.lb.presentation.game.model.GameCard
import io.lb.presentation.ui.theme.PrimaryBlue
import io.lb.presentation.ui.theme.PrimaryRed

@ExperimentalMaterial3Api
@Composable
fun MemoryGameCard(
    card: GameCard,
    onClick: () -> Unit
) {
    if (card.isFlipped) {
        val border = if (card.isMatched) {
            BorderStroke(2.dp, Color.Green)
        } else {
            BorderStroke(2.dp, PrimaryRed)
        }

        Card(
            modifier = Modifier
                .size(120.dp)
                .padding(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            border = border,
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp,
                pressedElevation = 16.dp
            ),
            onClick = {
                onClick()
            },
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = rememberAsyncImagePainter(
                        model = card.pokemonCard.imageUrl,
                    ),
                    contentDescription = "Pokemon Flipped Card"
                )
            }
        }
    } else {
        Card(
            modifier = Modifier
                .size(120.dp)
                .padding(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = PrimaryBlue
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp,
                pressedElevation = 16.dp
            ),
            onClick = {
                onClick()
            },
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.pokeball),
                    contentDescription = "Pokeball"
                )
            }
        }
    }
}
