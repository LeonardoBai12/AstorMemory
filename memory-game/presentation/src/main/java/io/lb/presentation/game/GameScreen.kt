package io.lb.presentation.game

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.lb.presentation.R
import io.lb.presentation.ui.components.LoadingIndicator
import io.lb.presentation.ui.components.MemoryGameCard
import io.lb.presentation.ui.components.MemoryGameRedButton
import io.lb.presentation.ui.components.MemoryGameRestartButton
import io.lb.presentation.ui.components.MemoryGameStopButton
import io.lb.presentation.ui.navigation.MemoryGameScreens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
internal fun GameScreen(
    navController: NavController,
    isDarkMode: Boolean,
    viewModel: GameViewModel = hiltViewModel<GameViewModel>(),
    cardsPerLine: Int,
    cardsPerColumn: Int,
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
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            GameTopBar(navController, state, viewModel, lastSelectedCard, isDarkMode)
        }
    ) { padding ->
        if (state.isLoading) {
            LoadingIndicator(screenHeight = screenHeight)
        } else if (state.message.isNullOrEmpty().not() ||
            state.cards.isEmpty() ||
            state.message == "null"
        ) {
            ErrorMessage(padding, state, viewModel)
        } else {
            CardGrid(
                viewModel = viewModel,
                padding = padding,
                state = state,
                lastSelectedCard = lastSelectedCard,
                cardsPerLine = cardsPerLine,
                cardsPerColumn = cardsPerColumn,
                onCardFlipped = onCardFlipped,
                onCardMatched = {
                    onCardMatched(state.cards.filter { it.isMatched }.size)
                }
            )
        }
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
                } ?: stringResource(R.string.ops_something_went_wrong),
                fontWeight = FontWeight.W600,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            MemoryGameRedButton(stringResource(R.string.try_again)) {
                viewModel.onEvent(GameEvent.OnRequestGames)
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
private fun GameTopBar(
    navController: NavController,
    state: GameState,
    viewModel: GameViewModel,
    lastSelectedCard: MutableState<String>,
    isDarkMode: Boolean
) {
    TopAppBar(
        modifier = Modifier.padding(top = 8.dp),
        title = {
            Column(
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .fillMaxWidth(),
                    text = if (state.isLoading.not() && state.score > 0) {
                        "${state.score} pts"
                    } else {
                        ""
                    },
                    fontSize = 32.sp,
                    fontWeight = FontWeight.W600,
                    textAlign = TextAlign.End
                )
                if (state.currentCombo > 1) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .fillMaxWidth(),
                        text = stringResource(R.string.combo_bonus, (state.currentCombo) * 10),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W600,
                        textAlign = TextAlign.End,
                        color = if (isDarkMode) {
                            Color.Yellow
                        } else {
                            Color.DarkGray
                        }
                    )
                } else {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        },
        navigationIcon = {
            Row {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .fillMaxWidth(0.2f)
                ) {
                    MemoryGameStopButton {
                        navController.navigate(MemoryGameScreens.Menu.name) {
                            popUpTo(MemoryGameScreens.Menu.name) {
                                inclusive = true
                            }
                        }
                    }
                }
                if (state.isLoading.not() && state.message.isNullOrEmpty() &&
                    state.cards.any { it.isMatched.not() }) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .fillMaxWidth(0.3f)
                    ) {
                        MemoryGameRestartButton {
                            lastSelectedCard.value = ""
                            viewModel.onEvent(GameEvent.GameRestarted)
                        }
                    }
                }
            }
        }
    )
}

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
private fun CardGrid(
    viewModel: GameViewModel,
    padding: PaddingValues,
    state: GameState,
    lastSelectedCard: MutableState<String>,
    cardsPerLine: Int,
    cardsPerColumn: Int,
    onCardFlipped: () -> Unit,
    onCardMatched: () -> Unit,
) {
    val clickLock = remember {
        mutableStateOf(false)
    }
    LazyVerticalGrid(
        modifier = Modifier
            .padding(padding)
            .padding(top = 12.dp)
            .padding(horizontal = 12.dp),
        columns = GridCells.Fixed(cardsPerLine),
        userScrollEnabled = true,
    ) {
        items(state.cards.size) { index ->
            MemoryGameCard(
                card = state.cards[index],
                cardsPerLine = cardsPerLine,
                cardsPerColumn = cardsPerColumn
            ) {
                if (clickLock.value) {
                    return@MemoryGameCard
                }
                clickLock.value = true

                if (state.cards[index].isFlipped || state.cards[index].isMatched) {
                    return@MemoryGameCard
                }
                if (state.cards.filter { it.isFlipped && it.isMatched.not() }.size >= 2) {
                    clickLock.value = false
                    return@MemoryGameCard
                }

                onCardFlipped()
                afterCardFlipped(
                    lastSelectedCard = lastSelectedCard,
                    state = state,
                    index = index,
                    viewModel = viewModel,
                    onCardMatched = onCardMatched
                )

                resetClickLock(clickLock)
            }
        }
    }
}

private fun afterCardFlipped(
    lastSelectedCard: MutableState<String>,
    state: GameState,
    index: Int,
    viewModel: GameViewModel,
    onCardMatched: () -> Unit
) {
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

private fun resetClickLock(clickLock: MutableState<Boolean>) {
    CoroutineScope(Dispatchers.Main).launch {
        delay(100)
        clickLock.value = false
    }
}
