package io.lb.presentation.ui.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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

        FlippedCard(border, onClick, card)
    } else {
        NotFlippedCard(onClick)
    }
}

@ExperimentalMaterial3Api
@Composable
private fun NotFlippedCard(onClick: () -> Unit) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp

    Card(
        modifier = Modifier
            .height((screenHeight / 6.35).dp)
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

@ExperimentalMaterial3Api
@Composable
private fun FlippedCard(
    border: BorderStroke,
    onClick: () -> Unit,
    card: GameCard
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp

    Card(
        modifier = Modifier
            .height((screenHeight / 6.35).dp)
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
                bitmap = convertImageByteArrayToBitmap(card.pokemonCard.imageData)
                    .asImageBitmap(),
                contentDescription = "Pokemon Flipped Card"
            )
        }
    }
}

fun convertImageByteArrayToBitmap(imageData: ByteArray): Bitmap {
    return BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
}