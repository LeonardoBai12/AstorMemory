package io.lb.presentation.scores

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.lb.presentation.R
import io.lb.presentation.ui.components.MemoryGameWhiteButton
import io.lb.presentation.util.toDateFormat

@Composable
internal fun ScoreScreen(
    navController: NavController,
    viewModel: ScoreViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value

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
                contentDescription = "Pokemon Game Logo",
            )

            if (state.isLoading) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Loading...",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            } else if (state.message.isNullOrEmpty().not()) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = state.message.orEmpty(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            } else {
                ScoresColumn(state)
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                MemoryGameWhiteButton(
                    text = "BACK",
                    onClick = {
                        navController.navigateUp()
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

@Composable
private fun ScoresColumn(state: ScoreState) {
    LazyColumn(
        userScrollEnabled = true
    ) {
        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
        items(state.scores.size) { index ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "${index + 1}.",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = state.scores[index].score.toString(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "(${state.scores[index].amount} cards)",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = state.scores[index].timeMillis.toDateFormat(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
