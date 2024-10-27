package io.lb.presentation.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.lb.presentation.ui.components.MemoryGameCard
import io.lb.presentation.ui.navigation.MemoryGameScreens
import kotlinx.coroutines.flow.collectLatest

@ExperimentalMaterial3Api
@Composable
internal fun GameScreen(
    navController: NavController,
    viewModel: GameViewModel = hiltViewModel<GameViewModel>(),
    onCardFlipped: () -> Unit,
    onCardMatched: () -> Unit,
) {
    val state = viewModel.state.collectAsState().value
    val lastSelectedCard = remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = "GameScreen") {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is GameViewModel.UiEvent.Finish -> {
                    navController.navigate(MemoryGameScreens.GameOver.name + "/${event.score}")
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Memory Game",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.W600
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }
            )
        }
    ) { padding ->
        if (state.isLoading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Loading cards...", fontSize = 24.sp)
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressIndicator()
            }
        } else if (state.message.isNullOrEmpty().not()) {
            Surface(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = state.message.orEmpty())
                }
            }
        } else {
            LazyVerticalGrid(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp),
                columns = GridCells.Adaptive(88.dp),
                userScrollEnabled = true,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.cards.size) { index ->
                    MemoryGameCard(state.cards[index]) {
                        if (state.cards[index].isFlipped || state.cards[index].isMatched) {
                            return@MemoryGameCard
                        }

                        if (state.cards.filter { it.isFlipped && it.isMatched.not() }.size == 2)
                            return@MemoryGameCard

                        onCardFlipped()

                        if (lastSelectedCard.value.isEmpty()) {
                            lastSelectedCard.value = state.cards[index].pokemonCard.name
                            viewModel.onEvent(GameEvent.CardFlipped(index))
                        } else if (lastSelectedCard.value != state.cards[index].pokemonCard.name) {
                            viewModel.onEvent(GameEvent.CardFlipped(index))
                            viewModel.onEvent(GameEvent.CardMismatched)
                            lastSelectedCard.value = ""
                        } else {
                            viewModel.onEvent(GameEvent.CardFlipped(index))
                            viewModel.onEvent(GameEvent.CardMatched(id = state.cards[index].pokemonCard.id))
                            lastSelectedCard.value = ""
                            onCardMatched()
                        }
                    }
                }
            }
        }
    }
}
