package io.lb.presentation.scores

sealed class ScoresEvent {
    data class OnRequestScores(val amount: Int) : ScoresEvent()
}
