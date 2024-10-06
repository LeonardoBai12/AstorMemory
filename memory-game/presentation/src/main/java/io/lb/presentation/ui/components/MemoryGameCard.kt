package io.lb.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.lb.presentation.R
import io.lb.presentation.ui.theme.PrimaryBlue

@ExperimentalMaterial3Api
@Composable
fun MemoryGameCard(
    onClick: () -> Unit
) {
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
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.pokeball),
                contentDescription = "Pokeball"
            )
        }
    }
}
