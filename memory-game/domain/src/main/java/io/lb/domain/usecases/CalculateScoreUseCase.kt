package io.lb.domain.usecases

/**
 * Use case to calculate the score.
 */
class CalculateScoreUseCase {
    /**
     * Calculates the score based on the amount of cards and the number of attempts.
     *
     * @param amount The amount of cards.
     * @param mismatches The number of attempts.
     * @return The calculated score.
     */
    operator fun invoke(amount: Int, mismatches: Int): Int {
        val maxScore = MAX_SCORE * amount
        val penalty = mismatches * PENALTY
        return (maxScore - penalty).coerceAtLeast(0)
    }

    companion object {
        private const val PENALTY = 10
        private const val MAX_SCORE = 100
    }
}
