package io.lb.presentation.scores

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.lb.common.data.model.Score
import io.lb.common.shared.flow.Resource
import io.lb.domain.use_cases.MemoryGameUseCases
import io.lb.presentation.game.model.GameCard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
}