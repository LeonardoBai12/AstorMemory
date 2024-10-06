package io.lb.domain.use_cases

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
        val maxScore = 100 * amount
        val penalty = mismatches * 10
        return (maxScore - penalty).coerceAtLeast(0)
    }
}
