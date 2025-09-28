package io.lb.presentation.ui.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.lb.presentation.R
import io.lb.presentation.game.model.GameCard
import io.lb.presentation.ui.theme.Dimens
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
    val flipRotation by animateFloatAsState(
        targetValue = if (card.isFlipped) 180f else 0f,
        animationSpec = tween(
            durationMillis = 400,
            easing = EaseInOutCubic
        ),
        label = "cardFlip"
    )

    Box(
        modifier = Modifier
            .graphicsLayer {
                rotationY = flipRotation
                cameraDistance = 12f * density
            }
    ) {
        if (flipRotation <= 90f) {
            NotFlippedCard(cardsPerLine, cardsPerColumn, onClick)
        } else {
            val border = if (card.isMatched) {
                BorderStroke(2.dp, Color.Green)
            } else {
                BorderStroke(2.dp, PrimaryRed)
            }

            Box(
                modifier = Modifier.graphicsLayer {
                    rotationY = 180f
                }
            ) {
                FlippedCard(border, cardsPerLine, cardsPerColumn, card)
            }
        }
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
            .height(getCardHeight(cardsPerColumn, screenHeight))
            .padding(
                getCardPadding(cardsPerLine)
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = Dimens.defaultElevation,
            pressedElevation = Dimens.pressedElevation
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
                painter = painterResource(id = R.drawable.narcisus),
                contentDescription = "Narcisus",
                contentScale = ContentScale.Fit
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
            .heightIn(max = getCardHeight(cardsPerColumn, screenHeight))
            .padding(
                getCardPadding(cardsPerLine)
            )
            .clip(CardDefaults.shape)
            .combinedClickable(
                onClick = {
                },
                onLongClick = {
                    Toast.makeText(context, card.astorCard.name, Toast.LENGTH_SHORT).show()
                },
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
        ),
        border = border,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                bitmap = convertImageByteArrayToBitmap(card.astorCard.imageData)
                    .asImageBitmap(),
                contentDescription = "Astor Flipped Card"
            )
        }
    }
}

fun convertImageByteArrayToBitmap(imageData: ByteArray): Bitmap {
    return BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
}
