package io.lb.presentation.game

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.lb.common.data.model.PokemonCard
import io.lb.common.shared.flow.Resource
import io.lb.domain.usecases.MemoryGameUseCases
import io.lb.presentation.game.model.GameCard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class GameViewModel @Inject constructor(
    private val useCases: MemoryGameUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state: MutableStateFlow<GameState> = MutableStateFlow(GameState())
    val state: StateFlow<GameState> = _state

    private var getCardsJob: Job? = null
    private val games = mutableListOf<PokemonCard>()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class UiEvent {
        data class Finish(val score: Int) : UiEvent()
    }

    private var mismatches = 0
    private val amount = savedStateHandle["amount"] ?: DEFAULT_CARD_AMOUNT

    init {
        _state.update {
            if (amount > 1) {
                it.copy(score = amount * 100)
            } else {
                it
            }
        }
        getGames(amount)
    }

    fun onEvent(event: GameEvent) {
        when (event) {
            is GameEvent.CardFlipped -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            cards = it.cards.mapIndexed { index, gameCard ->
                                if (index == event.index) {
                                    gameCard.copy(isFlipped = !gameCard.isFlipped)
                                } else {
                                    gameCard
                                }
                            }
                        )
                    }
                }
            }
            is GameEvent.CardMatched -> {
                onMatched(event)
            }
            GameEvent.CardMismatched -> {
                viewModelScope.launch {
                    onMismatched()
                }
            }
            GameEvent.GameFinished -> {
                viewModelScope.launch {
                    useCases.saveScoreUseCase(state.value.score)
                    delay(GET_SCORES_DELAY)
                    _eventFlow.emit(UiEvent.Finish(state.value.score))
                }
            }
        }
    }

    private fun onMatched(event: GameEvent.CardMatched) {
        viewModelScope.launch {
            _state.update {
                val currentState = it.copy(
                    cards = it.cards.map { gameCard ->
                        if (gameCard.pokemonCard.id == event.id) {
                            gameCard.copy(isMatched = true)
                        } else {
                            gameCard
                        }
                    }
                )
                currentState
            }
            if (_state.value.cards.all { gameCard -> gameCard.isMatched }) {
                onEvent(GameEvent.GameFinished)
            }
        }
    }

    private suspend fun onMismatched() {
        mismatches++
        delay(MISMATCH_DELAY)
        _state.update {
            it.copy(
                cards = it.cards.map { gameCard ->
                    if (gameCard.isMatched.not()) {
                        gameCard.copy(isFlipped = false)
                    } else {
                        gameCard
                    }
                },
                score = useCases.calculateScoreUseCase(amount, mismatches)
            )
        }
    }

    private fun getGames(amount: Int) {
        getCardsJob?.cancel()
        getCardsJob = useCases.getMemoryGameUseCase(amount).onEach { resource ->
            when (resource) {
                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            message = resource.message,
                            cards = emptyList()
                        )
                    }
                }

                is Resource.Loading -> {
                    _state.update {
                        it.copy(
                            isLoading = true,
                            message = null,
                            cards = emptyList()
                        )
                    }
                }

                is Resource.Success -> {
                    games.clear()
                    games.addAll(resource.data ?: emptyList())
                    _state.update {
                        it.copy(
                            cards = games.map { game -> GameCard(pokemonCard = game) },
                            isLoading = false,
                            message = null
                        )
                    }
                }
            }
        }.launchIn(CoroutineScope(Dispatchers.IO))
    }
    companion object {
        private const val DEFAULT_CARD_AMOUNT = 5
        private const val MISMATCH_DELAY = 750L
        private const val GET_SCORES_DELAY = 2000L
    }
}
