package io.lb.presentation.scores

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.lb.common.data.model.Score
import io.lb.common.shared.flow.Resource
import io.lb.domain.usecases.MemoryGameUseCases
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * ViewModel for the scores screen.
 *
 * @property useCases the use cases for the memory game.
 */
@HiltViewModel
internal class ScoreViewModel @Inject constructor(
    private val useCases: MemoryGameUseCases,
) : ViewModel() {
    private val _state: MutableStateFlow<ScoreState> = MutableStateFlow(ScoreState())
    val state: StateFlow<ScoreState> = _state

    private var getScoresJob: Job? = null
    private val scores = mutableListOf<Score>()

    init {
        getScores()
    }

    fun onEvent(event: ScoresEvent) {
        when (event) {
            is ScoresEvent.OnRequestScores -> {
                if (event.amount != 0) {
                    getScoresByAmount(event.amount)
                } else {
                    getScores()
                }
            }
        }
    }

    private fun getScoresByAmount(amount: Int) {
        getScoresJob?.cancel()
        getScoresJob = useCases.getScoresByAmountUseCase(amount).onEach { resource ->
            when (resource) {
                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            message = resource.message,
                            scores = emptyList()
                        )
                    }
                }

                is Resource.Loading -> {
                    _state.update {
                        it.copy(
                            isLoading = true,
                            message = null,
                            scores = emptyList()
                        )
                    }
                }

                is Resource.Success -> {
                    scores.clear()
                    scores.addAll(resource.data ?: emptyList())
                    delay(300)
                    _state.update {
                        it.copy(
                            scores = scores,
                            isLoading = false,
                            message = null
                        )
                    }
                }
            }
        }.launchIn(CoroutineScope(Dispatchers.IO))
    }

    private fun getScores() {
        getScoresJob?.cancel()
        getScoresJob = useCases.getScoresUseCase().onEach { resource ->
            when (resource) {
                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            message = resource.message,
                            scores = emptyList()
                        )
                    }
                }

                is Resource.Loading -> {
                    _state.update {
                        it.copy(
                            isLoading = true,
                            message = null,
                            scores = emptyList()
                        )
                    }
                }

                is Resource.Success -> {
                    scores.clear()
                    scores.addAll(resource.data ?: emptyList())
                    delay(300)

                    if (state.value.filters.isNotEmpty()) {
                        _state.update {
                            it.copy(
                                scores = scores.take(10),
                                isLoading = false,
                                message = null
                            )
                        }
                        return@onEach
                    }
                    val availableAmounts = scores.map { it.amount }.distinct().toMutableList()
                    availableAmounts.add(0)

                    _state.update {
                        it.copy(
                            scores = scores.take(10),
                            isLoading = false,
                            filters = availableAmounts.sorted(),
                            message = null
                        )
                    }
                }
            }
        }.launchIn(CoroutineScope(Dispatchers.IO))
    }
}
