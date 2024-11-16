package io.lb.presentation.ui.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.lb.presentation.R
import io.lb.presentation.game.model.GameCard
import io.lb.presentation.ui.theme.PrimaryBlue
import io.lb.presentation.ui.theme.PrimaryRed

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun MemoryGameCard(
    card: GameCard,
    cardsPerLine: Int = 4,
    cardsPerColumn: Int = 6,
    onClick: () -> Unit
) {
    if (card.isFlipped) {
        val border = if (card.isMatched) {
            BorderStroke(2.dp, Color.Green)
        } else {
            BorderStroke(2.dp, PrimaryRed)
        }

        FlippedCard(border, cardsPerLine, cardsPerColumn, card)
    } else {
        NotFlippedCard(cardsPerLine, cardsPerColumn, onClick)
    }
}

@ExperimentalMaterial3Api
@Composable
private fun NotFlippedCard(
    cardsPerLine: Int,
    cardsPerColumn: Int,
    onClick: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp

    Card(
        modifier = Modifier
            .height(
                getCardHeight(cardsPerColumn, screenHeight)
            )
            .padding(
                getCardPadding(cardsPerLine)
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary
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

@Composable
private fun getCardPadding(cardsPerLine: Int) = if (cardsPerLine <= 5) {
    4.dp
} else {
    2.dp
}

@Composable
private fun getCardHeight(cardsPerColumn: Int, screenHeight: Int) = when (cardsPerColumn) {
    6 -> {
        (screenHeight / 6.35).dp
    }
    5 -> {
        (screenHeight / 5.1).dp
    }
    8 -> {
        (screenHeight / 8.5).dp
    }
    7 -> {
        (screenHeight / 7.4).dp
    }
    else -> {
        (screenHeight / 9.35).dp
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
private fun FlippedCard(
    border: BorderStroke,
    cardsPerLine: Int,
    cardsPerColumn: Int,
    card: GameCard
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .height(
                getCardHeight(cardsPerColumn, screenHeight)
            )
            .padding(
                getCardPadding(cardsPerLine)
            )
            .clip(CardDefaults.shape)
            .combinedClickable(
                onClick = {
                },
                onLongClick = {
                    Toast.makeText(context, card.pokemonCard.name, Toast.LENGTH_SHORT).show()
                },
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
        ),
        border = border,
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