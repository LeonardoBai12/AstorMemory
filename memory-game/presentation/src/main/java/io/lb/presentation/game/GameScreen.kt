package io.lb.presentation.game

import android.content.Context
import android.net.ConnectivityManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getSystemService
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.lb.presentation.R
import io.lb.presentation.ui.components.MemoryGameBlueButton
import io.lb.presentation.ui.components.MemoryGameCard
import io.lb.presentation.ui.components.MemoryGameRedButton
import io.lb.presentation.ui.navigation.MemoryGameScreens
import kotlinx.coroutines.flow.collectLatest

@ExperimentalMaterial3Api
@Composable
internal fun GameScreen(
    navController: NavController,
    viewModel: GameViewModel = hiltViewModel<GameViewModel>(),
    onCardFlipped: () -> Unit,
    onCardMatched: (Int) -> Unit,
) {
    val state = viewModel.state.collectAsState().value
    val lastSelectedCard = remember {
        mutableStateOf("")
    }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenWidthDp.dp

    LaunchedEffect(key1 = "GameScreen") {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is GameViewModel.UiEvent.Finish -> {
                    navController.navigate(MemoryGameScreens.GameOver.name + "/${event.score}/${state.amount}")
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.White,
        topBar = {
            GameTopBar(navController, state)
        }
    ) { padding ->
        if (state.isLoading) {
            LoadingIndicator(screenHeight)
        } else if (state.message.isNullOrEmpty().not() ||
            state.cards.isEmpty() ||
            state.message == "null"
        ) {
            ErrorMessage(padding, state, viewModel)
        } else {
            CardGrid(
                padding = padding,
                state = state,
                onCardFlipped = onCardFlipped,
                lastSelectedCard = lastSelectedCard,
                viewModel = viewModel,
                onCardMatched = {
                    onCardMatched(state.cards.filter { it.isMatched }.size)
                }
            )
        }
    }
}

@Composable
private fun LoadingIndicator(screenHeight: Dp) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
                .size(screenHeight / 6),
            color = Color.Red,
            strokeWidth = 5.dp,
        )
        Image(
            modifier = Modifier.size(screenHeight / 8),
            painter = painterResource(id = R.drawable.pokeball),
            contentDescription = "PokeBall",
        )
    }
}

@Composable
private fun ErrorMessage(
    padding: PaddingValues,
    state: GameState,
    viewModel: GameViewModel
) {
    Surface(
        modifier = Modifier
            .padding(padding)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = state.message.takeIf {
                    it.isNullOrEmpty().not() && it != "null"
                } ?: "Ops! Something went wrong",
                fontWeight = FontWeight.W600,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            MemoryGameRedButton("Try again") {
                viewModel.onEvent(GameEvent.OnRequestGames)
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
private fun GameTopBar(navController: NavController, state: GameState) {
    TopAppBar(
        modifier = Modifier.padding(top = 8.dp),
        title = {
            Text(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth(),
                text = if (state.isLoading.not() && state.score > 0) {
                    "Score: ${state.score}"
                } else {
                    ""
                },
                fontSize = 32.sp,
                fontWeight = FontWeight.W600,
                textAlign = TextAlign.End
            )
        },
        navigationIcon = {
            Box(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth(0.4f)
            ) {
                MemoryGameBlueButton("STOP") {
                    navController.navigateUp()
                }
            }
        }
    )
}

@ExperimentalMaterial3Api
@Composable
private fun CardGrid(
    padding: PaddingValues,
    state: GameState,
    onCardFlipped: () -> Unit,
    lastSelectedCard: MutableState<String>,
    viewModel: GameViewModel,
    onCardMatched: () -> Unit,
) {
    LazyVerticalGrid(
        modifier = Modifier
            .padding(padding)
            .padding(16.dp),
        columns = GridCells.Fixed(4),
        userScrollEnabled = true,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(state.cards.size) { index ->
            MemoryGameCard(state.cards[index]) {
                if (state.cards[index].isFlipped || state.cards[index].isMatched) {
                    return@MemoryGameCard
                }

                if (state.cards.filter { it.isFlipped && it.isMatched.not() }.size == 2) {
                    return@MemoryGameCard
                }

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
                    viewModel.onEvent(
                        GameEvent.CardMatched(id = state.cards[index].pokemonCard.pokemonId)
                    )
                    lastSelectedCard.value = ""
                    onCardMatched()
                }
            }
        }
    }
}
