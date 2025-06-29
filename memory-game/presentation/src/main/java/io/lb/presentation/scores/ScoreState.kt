package io.lb.presentation.scores

import io.lb.common.data.model.Score

internal data class ScoreState(
    val scores: List<Score> = emptyList(),
    val filters: List<Int> = emptyList(),
    val message: String? = null,
    val isLoading: Boolean = true,
)
