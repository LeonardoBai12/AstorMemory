package io.lb.presentation.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.lb.common.data.model.PokemonCard
import io.lb.common.shared.flow.Resource
import io.lb.domain.use_cases.MemoryGameUseCases
import io.lb.presentation.game.model.GameCard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class GameViewModel @Inject constructor(
    private val useCases: MemoryGameUseCases
) : ViewModel() {
    private val _state: MutableStateFlow<GameState> = MutableStateFlow(GameState())
    val state: StateFlow<GameState> = _state

    private val games = mutableListOf<PokemonCard>()

    fun onEvent(event: GameEvent) {
        when (event) {
            is GameEvent.CardFlipped -> TODO()
            is GameEvent.CardMatched -> TODO()
            is GameEvent.CardMismatched -> TODO()
            GameEvent.GameFinished -> TODO()
            is GameEvent.GetCards -> {
                getGames(event.amount)
            }
        }
    }

    private fun getGames(amount: Int) {
        viewModelScope.launch {
            useCases.getMemoryGameUseCase(amount).onEach { resource ->
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
            }
        }
    }
}
